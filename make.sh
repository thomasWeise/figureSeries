#!/bin/bash

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
rm figureSeries.sty

latex figureSeries.ins

cd ./examples
./make.sh
cd ..

cd ./errorExamples
./make.sh
cd ..

pdflatex figureSeries.dtx
pdflatex figureSeries.dtx

makeindex -s gglo.ist -o figureSeries.gls figureSeries.glo
makeindex -s gind.ist -o figureSeries.ind figureSeries.idx

pdflatex figureSeries.dtx

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

evince figureSeries.pdf
