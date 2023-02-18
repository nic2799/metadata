import lxml.etree as ET

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






# Carica il file XML
tree = ET.parse("metadata_pico.xml")
root = tree.getroot()

# Crea un nuovo elemento come radice
new_root = ET.Element("{http://www.w3.org/1999/02/22-rdf-syntax-ns#}RDF", nsmap=namespaces)




# Sposta tutti i figli della vecchia radice al nuovo elemento
for child in root:
    new_root.append(child)

# Sostituisci la vecchia radice con il nuovo elemento
tree._setroot(new_root)

root = tree.getroot()


has_preview = False
for elem in root.iter():
    if elem.tag == "{http://purl.org/pico/1.0/}preview":
        has_preview = True
        break

if has_preview:
    type_value = "IMAGE"
    type_tag = ET.Element(f"{{{namespaces['edm']}}}type")
    type_tag.text = type_value
    root.append(type_tag)

for elem in root.iter():
    if elem.tag in pico_to_edm:
        elem.tag = pico_to_edm[elem.tag]

# Scrivi il file XML con la nuova radice
tree.write("new_roo.xml", xml_declaration=True, encoding='utf-8')