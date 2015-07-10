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

pdflatex -interaction=batchmode -halt-on-error errorExample_1.tex
pdflatex -interaction=batchmode -halt-on-error errorExample_2.tex
pdflatex -interaction=batchmode -halt-on-error errorExample_3.tex
pdflatex -interaction=batchmode -halt-on-error errorExample_4.tex
pdflatex -interaction=batchmode -halt-on-error errorExample_5.tex

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
rm figureSeries.sty
rm IEEEtran.cls