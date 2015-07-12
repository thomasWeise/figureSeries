#!/bin/bash

rm figureSeries.*
rm texput.log
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

cp ../figureSeries.sty .

for document in `find . -type f -name "*.tex"`
do
filename=$(basename "$document")
filename="${filename%.*}"
pdflatex -interaction=batchmode -halt-on-error ${filename}
pdflatex -interaction=batchmode -halt-on-error ${filename}
pdflatex -interaction=batchmode -halt-on-error ${filename}
done

rm figureSeries.*
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
rm texput.log