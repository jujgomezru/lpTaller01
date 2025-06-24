#!/bin/bash

# Si el directorio "lpTaller01" existe en el directorio actual, cambia a ese directorio
if [[ -d "lpTaller01" ]]; then
    cd lpTaller01
# De lo contrario, sube en la jerarquía de directorios hasta que el directorio actual sea "lpTaller01"
elif [[ ! $(pwd) =~ lpTaller01$ ]]; then
    while [[ ! $(pwd) =~ lpTaller01$ ]]; do
        cd ..
    done
fi

echo "Generando analizador léxico java, directorio actual: $(pwd)"

jflex -d src/model/scanner/ src/data/Lexer.flex