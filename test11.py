import json
#per acquisire dc devi anda in metadata e prendi i label
from lxml import etree


namespaces = {
    'rdf': "http://www.w3.org/1999/02/22-rdf-syntax-ns#",
    'dc': "http://purl.org/dc/elements/1.1/",
    'dcterms': "http://purl.org/dc/terms/",
    'edm': "http://www.europeana.eu/schemas/edm/",
    'owl': "http://www.w3.org/2002/07/owl#",
    'wgs84_pos': "http://www.w3.org/2003/01/geo/wgs84_pos#"
}
pico_to_edm = {
   # "{http://purl.org/dc/elements/1.1/}type": "edm:type",
}



xml = etree.Element("{http://www.w3.org/1999/02/22-rdf-syntax-ns#}RDF", nsmap=namespaces)
# carica il file JSON
with open("iiif.json") as f:
    data = json.load(f)




# cerca il valore all'interno di canvases -> images -> resource -> id
for seq in data.get("sequences", []):
    can = seq.get("canvases", [])
    for canvas in can:
        images = canvas.get("images",[])
        for image in images:
            res = image.get("resource",[])
            if res:
                id = res.get("@id")
               # print(id)
                
                  # crea un nuovo elemento edm_WebResource e inserisci l'id all'interno
                web_resource = etree.Element(f"{{{namespaces['edm']}}}WebResourse",attrib = {"{http://www.w3.org/1999/02/22-rdf-syntax-ns#}about": id})
                dcterms_isReferencedBy = etree.Element(f"{{{namespaces['dcterms']}}}isReferencedBy", attrib = {"{http://www.w3.org/1999/02/22-rdf-syntax-ns#}resource": data.get("@id")})
                web_resource.append(dcterms_isReferencedBy)
               # web_resource.text = id   (f"{{{namespaces['edm']}}}WebResourse",attrib = {"{http://www.w3.org/1999/02/22-rdf-syntax-ns#}about": id})
                
                        


# crea un nuovo documento XML

xml.append(web_resource)
#xml.append(web_resource)

# scrivi il file XML


def Search_dc(json_data):
    dc = {
    
        "Title": "",
        "Type": "",
        "Location": "",
        "Description": "",
        "Date": "",
        "Format": ""
    }
    for metadata in json_data["metadata"]:
        label = metadata["label"]
        value = metadata["value"]
        if "title" in label.lower():
            dc["Title"] = value
        elif "type" in label.lower():
            dc["Type"] = value
        elif "location" in label.lower():
            dc["Location"] = value
        elif "summary" in label.lower():
            dc["Description"] = value
        elif "date" in label.lower():
            dc["Date"] = value
        elif "prova" in label.lower():
            dc["format"] = value
            
        # inserire altre condizioni per altri campi del Dublin Core se necessario
    return dc

cd = Search_dc(data)
print(cd["Date"])
for k in cd.keys():
 dc_resource = etree.Element(f"{{{namespaces['dc']}}}" + k )
# dc_resource.text = cd[k]
 if cd[k] == '' or not cd[k]:
    print("Inserisci il valore di " + k)
    testo = input()
    dc_resource.text = testo
 else:
    dc_resource.text = cd[k]
# print(k + "->" + cd[k])
 xml.append(dc_resource)
 print(dc_resource)
 
 
 
 
 with open("output.xml", "wb") as f:
    f.write(etree.tostring(xml, pretty_print=True))