
import lxml.etree as ET

tree = ET.parse("metadati_pico.xml")
root = tree.getroot()

for elem in root.iter("Author"):
    # Crea un nuovo elemento con un tag diverso
    new_elem = ET.Element("autore")
    new_elem.text = elem.text
    # Sostituisci l'elemento vecchio con il nuovo
    elem.getparent().replace(elem, new_elem)

tree.write("metadati_pico_modifici.xml")