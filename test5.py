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

tree = ET.parse("metadata_pico.xml")
root = tree.getroot()

for elem in root.iter():
    print(elem.tag)

    print("-------------------")#quindi poiche c'è preview possiamo dire
    print(elem.text)
    if elem.text == "StillImage" :
     print("hello")
     type_value = "IMAGE"
     type_tag = ET.Element("{http://www.europeana.eu/schemas/edm/}type", nsmap={'edm': "http://www.europeana.eu/schemas/edm/"})

     type_tag.text = type_value
     elem.addnext(type_tag)

    if elem.tag in pico_to_edm:
        print(elem.tag)
        print(pico_to_edm[elem.tag])
        elem.tag = pico_to_edm[elem.tag]

tree.write("mt.xml")


