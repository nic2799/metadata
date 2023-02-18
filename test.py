from lxml import etree

tree = etree.parse("metadati_pico.xml")
root = tree.getroot()

# Modifica il contenuto di un tag XML
for elem in root.xpath("//Title"):
    elem.text = "ciao"

# Scrivi i metadati modificati su un nuovo file
tree.write("metadati_pic_modificati.xml")