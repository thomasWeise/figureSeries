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
retA=$?
echo "pdflatex exit code: ${retA}" >> errorExample_1.log
pdflatex -interaction=batchmode -halt-on-error errorExample_2.tex
retB=$?
echo "pdflatex exit code: ${retB}" >> errorExample_2.log
pdflatex -interaction=batchmode -halt-on-error errorExample_3.tex
retC=$?
echo "pdflatex exit code: ${retC}" >> errorExample_3.log
pdflatex -interaction=batchmode -halt-on-error errorExample_4.tex
retD=$?
echo "pdflatex exit code: ${retD}" >> errorExample_4.log
pdflatex -interaction=batchmode -halt-on-error errorExample_5.tex
retE=$?
echo "pdflatex exit code: ${retE}" >> errorExample_5.log

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

echo "Exit codes: ${retA} ${retB} ${retC} ${retD} ${retE}"