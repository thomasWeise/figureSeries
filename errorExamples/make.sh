#!/bin/bash

rm figureSeries.sty
rm IEEEtran.cls
rm *.aux
rm *.dvi
rm *.glo
rm *.gls
rm *.idx
rm *.ilg
rm *.ind
rm *.log
rm *.out
rm *.ps
rm *.toc
rm *.pdf

cp ../figureSeries.sty ./figureSeries.sty
cp ../examples/IEEEtran.cls ./IEEEtran.cls

retList=""

for document in `find . -type f -name "*.tex"`
do
filename=$(basename "$document")
filename="${filename%.*}"

pdflatex -interaction=batchmode -halt-on-error ${filename}
retVal1=$?
pdflatex -interaction=batchmode -halt-on-error ${filename}
retVal2=$?
pdflatex -interaction=batchmode -halt-on-error ${filename}
retVal3=$?

exitcodes="${filename}: ${retVal1} ${retVal2} ${retVal3}"
retList="${retList}\n${exitcodes}"
echo "exit codes of three pdflatex runs: ${exitcodes}" >> "${filename}.log"

done

rm *.aux
rm *.dvi
rm *.glo
rm *.gls
rm *.idx
rm *.ilg
rm *.ind
rm *.out
rm *.ps
rm *.toc
rm *.pdf
rm *.log
rm figureSeries.sty
rm IEEEtran.cls

echo -e "Exit codes: ${retList}"