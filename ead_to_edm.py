import json
#per acquisire dc devi anda in metadata e prendi i label
from lxml import etree
import lxml.etree as ET

namespaces = {
    'rdf': "http://www.w3.org/1999/02/22-rdf-syntax-ns#",
    'dc': "http://purl.org/dc/elements/1.1/",
    'dcterms': "http://purl.org/dc/terms/",
    'edm': "http://www.europeana.eu/schemas/edm/",
    'owl': "http://www.w3.org/2002/07/owl#",
    'wgs84_pos': "http://www.w3.org/2003/01/geo/wgs84_pos#"
}


xml = etree.Element("{http://www.w3.org/1999/02/22-rdf-syntax-ns#}RDF", nsmap=namespaces)
# carica il file JSON
tree = ET.parse("EAD.xml")
root = tree.getroot()

Web_resource = root.find(".//dao")
if Web_resource is not None:
        Edm_Web_resource = Web_resource.attrib.get("href")
def Search_dublincore(treee):
 dc = {
        "Title": "",
        "Type": "",
        "Location": "",
        "Description": "",
        "Date": "",
        "Format": ""
    }
 root = tree.getroot()  
    # Cerca il tag "title" all'interno del file XML
 title = root.find(".//unittitle")
 if title is not None:
        dc["Title"] = title.text
    
    # Cerca il tag "type" all'interno del file XML
 type_tag = root.find(".//type")
 if type_tag is not None:
        dc["Type"] = type_tag.text
    
    # Cerca il tag "location" all'interno del file XML
 location = root.find(".//originalsloc")
 if location is not None:
        dc["Location"] = location.text
    
    # Cerca il tag "description" all'interno del file XML

 description = root.find(".//description") 
 if description is not None:
        dc["Description"] = description.text
    
    # Cerca il tag "date" all'interno del file XML
 date = root.find(".//date")
 if date is not None:
        dc["Date"] = date.text
    
    # Cerca il tag "format" all'interno del file XML
 Web_resource = root.find(".//dao")
 if Web_resource is not None:
        dc["Format"] = Web_resource.attrib.get("href")
    
 return dc
       
       
print(Search_dublincore(tree))

#ore: aggregation indica che stiamo definendo un aggregazione   di risorse rd:about è URI DELL AGGREGAZIONE
#aggregateCHO invece indica che la risorsa è CHO di europezna

#Il tag edm:isShownAt indica l'URI della pagina web che mostra la risorsa.
#L'attributo rdf:resource specifica l'URI della pagina web.
#Il tag edm:isShownBy indica l'URI della rappresentazione digitale della risorsa.
#L'attributo rdf:resource specifica l'URI della rappresentazione digitale.
#Il tag edm:object indica l'URI della risorsa stessa.
#L'attributo rdf:resource specifica l'URI della risorsa.
#edm:providedCHO rappresenta un identificativo che rappresenta il C>HO