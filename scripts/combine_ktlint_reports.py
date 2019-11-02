# Adapted from Joe Birch's post: https://blog.bitrise.io/automating-code-review-tasks-for-multi-module-android-projects
# This takes all of our ktlint output XML files and combines them into one `ktlint-report.xml` file.
# This way we can pass one file into danger-checkstyle_format

import sys
import os.path
from xml.etree import ElementTree

first = None
file_list = [
"../app/build/reports/ktlint/ktlintMainSourceSetCheck.xml",
"../app/build/reports/ktlint/ktlintTestSourceSetCheck.xml",
"../app/build/reports/ktlint/ktlintAndroidTestSourceSetCheck.xml",
"../app/build/reports/detekt/detekt.xml",
"../image-loader/build/reports/ktlint/ktlintMainSourceSetCheck.xml",
"../image-loader/build/reports/detekt/detekt.xml",
"../database/build/reports/ktlint/ktlintMainSourceSetCheck.xml",
"../database/build/reports/detekt/detekt.xml"
]

ktlintFile = 'ktlint-report-orig.xml'
editedKtlintFile = 'ktlint-report.xml'

for filename in file_list:
    if os.path.isfile(filename):
        data = ElementTree.parse(filename).getroot()
        if first is None:
            first = data
        else:
            first.extend(data)
if first is not None:
    f = open( ktlintFile, 'w+' )
    f.write( "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" )
    f.write( ElementTree.tostring(first) )
    f.close()

delete_list = []
fin = open(ktlintFile)
fout = open(editedKtlintFile, "w+")
for line in fin:
    for word in delete_list:
        line = line.replace(word, "")
    fout.write(line)
    print(line)
fin.close()
fout.close()