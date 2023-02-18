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

# Crea il nuovo file XML con il tag RDF come root
xml = etree.Element("{http://www.w3.org/1999/02/22-rdf-syntax-ns#}RDF", nsmap=namespaces)

# Carica il file EAD
tree = ET.parse("EAD.xml")
root = tree.getroot()

# Trova il tag dao e aggiungi il link come EDM WebResource
Web_resource = root.find(".//dao")
if Web_resource is not None:
        Edm_Web_resource = Web_resource.attrib.get("href")
        web_resource = etree.Element(f"{{{namespaces['edm']}}}WebResource", attrib={"{http://www.w3.org/1999/02/22-rdf-syntax-ns#}about": Edm_Web_resource})
        xml.append(web_resource)

# Salva il nuovo file XML
with open("outpu12t.xml", "wb") as f:
    f.write(etree.tostring(xml, pretty_print=True))
