

import lxml.etree as ET

pico_to_edm = {
   # "{http://purl.org/dc/elements/1.1/}type": "edm:type",
}
    #"{http://purl.org/dc/elements/1.1/}title": "a",#si deve aggiungere url
   # "type": "dc:b",
   # "{http://purl.org/dc/elements/1.1/}creator":"d",
   # "oai_dc:dc/dc:type": "dc:type",
   # "{http://purl.org/dc/elements/1.1/}subject": "b",
  #  "oai_dc:dc/dc:description": "dc:description",
   # "oai_dc:dc/dc:publisher": "dc:publisher",
  #  "oai_dc:dc/dc:date": "dc:date",
 #   "oai_dc:dc/dc:format": "dc:format"
#}
ns={'edm': "http://www.europeana.eu/schemas/edm/"}
tree = ET.parse("metadata_pico.xml")
root = tree.getroot()


has_preview = False
for elem in root.iter():
    if elem.tag == "{http://purl.org/pico/1.0/}preview":
        has_preview = True
        break

if has_preview:
    type_value = "IMAGE"
    type_tag = ET.Element("type", nsmap=ns)
    type_tag.text = type_value
    root.addnext(type_tag)

for elem in root.iter():
    if elem.tag in pico_to_edm:
        elem.tag = pico_to_edm[elem.tag]

tree.write("m.xml")