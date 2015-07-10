#!/bin/bash


rm figureSeries.aux
rm figureSeries.dvi
rm figureSeries.glo
rm figureSeries.gls
rm figureSeries.idx
rm figureSeries.ilg
rm figureSeries.ind
rm figureSeries.log
rm figureSeries.out
rm figureSeries.ps
rm figureSeries.toc
rm figureSeries.sty
rm figureSeries.pdf
rm texput.log

cd ./examples
rm figureSeries.sty
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
rm example_1_LNCS.pdf
rm example_2_IEEEtran.pdf
rm example_3_IEEEtran.pdf
rm example_4_sigAlternate.pdf
rm example_5_sigAlternate.pdf
rm example_6_sigAlternate.pdf
rm example_7_LNCS.pdf
rm example_8.pdf
cd ..

latex figureSeries.ins

cd ./errorExamples
./make.sh
cd ..

cp figureSeries.sty ./examples/
cd ./examples
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
rm figureSeries.sty
cd ..

pdflatex figureSeries.dtx
pdflatex figureSeries.dtx

makeindex -s gglo.ist -o figureSeries.gls figureSeries.glo
makeindex -s gind.ist -o figureSeries.ind figureSeries.idx

pdflatex figureSeries.dtx

rm figureSeries.aux
rm figureSeries.dvi
rm figureSeries.glo
rm figureSeries.gls
rm figureSeries.idx
rm figureSeries.ilg
rm figureSeries.ind
rm figureSeries.log
rm figureSeries.out
rm figureSeries.ps
rm figureSeries.toc

cd ./examples
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
cd ..

rm texput.log

evince figureSeries.pdf
