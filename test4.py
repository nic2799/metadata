import lxml.etree as ET

pico_to_edm = {
    "{http://purl.org/dc/elements/1.1/}title": "a",#si deve aggiungere url
    "type": "dc:b",
    "{http://purl.org/dc/elements/1.1/}creator":"d",
    "oai_dc:dc/dc:type": "dc:type",
    "{http://purl.org/dc/elements/1.1/}subject": "b",
    "oai_dc:dc/dc:description": "dc:description",
    "oai_dc:dc/dc:publisher": "dc:publisher",
    "oai_dc:dc/dc:date": "dc:date",
    "oai_dc:dc/dc:format": "dc:format"
}

tree = ET.parse("metadati_pic.xml")
root = tree.getroot()

for elem in root.iter():
    #print(elem.tag)

    if elem.tag in pico_to_edm:
        print(elem.tag)
        print(pico_to_edm[elem.tag])
        elem.tag = pico_to_edm[elem.tag]

tree.write("metadata_qdm.xml")


