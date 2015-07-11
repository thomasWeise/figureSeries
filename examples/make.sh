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
pdflatex example_1_LNCS
pdflatex example_1_LNCS
pdflatex example_2_IEEEtran
pdflatex example_2_IEEEtran
pdflatex example_3_IEEEtran
pdflatex example_3_IEEEtran
pdflatex example_4_sigAlternate
pdflatex example_4_sigAlternate
pdflatex example_5_sigAlternate
pdflatex example_5_sigAlternate
pdflatex example_6_sigAlternate
pdflatex example_6_sigAlternate
pdflatex example_7_LNCS
pdflatex example_7_LNCS
pdflatex example_8
pdflatex example_8

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