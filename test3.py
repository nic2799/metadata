import lxml.etree as ET

# Carica il documento XML con i metadati PICO
tree = ET.parse("metadati_pic.xml")
root = tree.getroot()

# Crea un dizionario che mappa i tag PICO ai tag EDM
pico_to_edm = {
    "identifier": "dc:identifier",
    "title": "dc:title",
    "description": "dc:description",
    "creator": "dc:creator",
    "subject": "dc:subject",
    "publisher": "dc:publisher",
    "contributor": "dc:contributor",
    "date": "dc:date",
    "type": "dc:type",
    "format": "dc:format",
    "source": "dc:source",
    "language": "dc:language",
    "relation": "dc:relation",
    "coverage": "dc:coverage",
    "rights": "dc:rights",
    "Author": "autore",
}

# Itera su tutti i tag del documento
for elem in root.iter():
    # Se il tag è presente nel dizionario, modifica il tag
    if elem.tag in pico_to_edm:
        elem.tag = pico_to_edm[elem.tag]

# Scrivi il documento modificato su un nuovo file
tree.write("metadati_edm.xml", encoding="utf-8", xml_declaration=True)
