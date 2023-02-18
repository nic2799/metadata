import json
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

# Carica il file JSON
with open("meta_json.json", "r") as f:
    data = json.load(f)

# Crea un nuovo elemento come radice
new_root = ET.Element("{http://www.w3.org/1999/02/22-rdf-syntax-ns#}RDF", nsmap=namespaces)

has_preview = False
if "preview" in data:
    has_preview = True

if has_preview:
    type_value = "IMAGE"
    type_tag = ET.Element(f"{{{namespaces['edm']}}}type")
    type_tag.text = type_value
    new_root.append(type_tag)

# Aggiungi altri elementi come figli di new_root usando la struttura del file JSON
# ...

# Scrivi il file XML con la nuova radice
tree = ET.ElementTree(new_root)
tree.write("new_roo.xml", xml_declaration=True, encoding='utf-8')
