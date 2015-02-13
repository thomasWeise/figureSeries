del figureSeries.aux
del figureSeries.dvi
del figureSeries.glo
del figureSeries.gls
del figureSeries.idx
del figureSeries.ilg
del figureSeries.ind
del figureSeries.log
del figureSeries.out
del figureSeries.ps
del figureSeries.toc
del figureSeries.sty
del figureSeries.pdf

cd .\examples
del figureSeries.sty
del *.aux
del *.dvi
del *.glo
del *.gls
del *.idx
del *.ilg
del *.ind
del *.log
del *.out
del *.ps
del *.toc
del example_1_LNCS.pdf
del example_2_IEEEtran.pdf
del example_3_IEEEtran.pdf
del example_4_sigAlternate.pdf
del example_5_sigAlternate.pdf
del example_6_sigAlternate.pdf
del example_7_LNCS.pdf
cd ..

latex figureSeries.ins
copy figureSeries.sty .\examples\

cd .\examples
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
cd ..

pdflatex figureSeries.dtx
pdflatex figureSeries.dtx

makeindex -s gglo.ist -o figureSeries.gls figureSeries.glo
makeindex -s gind.ist -o figureSeries.ind figureSeries.idx

pdflatex figureSeries.dtx

del figureSeries.aux
del figureSeries.dvi
del figureSeries.glo
del figureSeries.gls
del figureSeries.idx
del figureSeries.ilg
del figureSeries.ind
del figureSeries.log
del figureSeries.out
del figureSeries.ps
del figureSeries.toc

cd .\examples
del *.aux
del *.dvi
del *.glo
del *.gls
del *.idx
del *.ilg
del *.ind
del *.log
del *.out
del *.ps
del *.toc
cd ..

figureSeries.pdf
