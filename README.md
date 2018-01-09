# Merge_pdf_sep
Merge a list of pdf files inserting a separator page  
Its a front/end to iText bsed doMerge code (found on Internet and GitHub) to insert separator pages when merging pdf files.   
Used to insert marker pages into piles of production printer printouts to separate print jobs  
See [https://github.com/gios-asu/html-validator/blob/master/src/com/asu/gios/PDFMerge.java]

## Getting Started

### Prerequisites
Developed and tested with:
 + Windows 7 64 bit
 + NetBeans IDE 8.0.2
 + java version "1.8.0_25"
 + Java(TM) SE Runtime Environment (build 1.8.0_25-b18)
 + itext-5.0.1.jar library [(https://sourceforge.net/projects/itext/files/iText/iText5.0.1/)]  

### Installing
If you want to compile/modify simply download merge_pdf_sep.java source, create a NetBeans or Eclipse IDE project, add itext-5.0.1.jar library and add/modify and compile

Otherwise download into your working directory merge_pdf_sep.jar and run it typing:

     java -jar merge_pdf_sep.jar filelist.txt "separator filename" x "output filename"


Parameters:  
 filelist.txt = text file containing filename list of pdf files to be merged (must end with an empty line)  
 separator filename = separator pdf file, same size as files to be merged  
 x = insert separator every x files merged.  
 output filename = merged output filename (optional)
If no "output filename" is given the merged filename will be the contatenation of source filenames.  
 
### Example
Download merge_pdf_sep.jar and the contents of "sample" folder into your working/test directory.  
This example has 4 pdf files, with different number of pages, we want to merge using the sequence  
contained in file pdf_list.txt and with a separator page each 2 files merged.  
The command will be:  
java -jar Merge_pdf_sep.jar pdf_list.txt "Separator A4.pdf" 2  
![alt text](https://github.com/fufububu/merge_pdf_sep/blob/master/sample1.png)  
will create an output file named *First_fileSecond_fileThird_file_Fourth_file.pdf*  
If we want a different ooutput filename simply add last parameter, "merged" in this example  
![alt text](https://github.com/fufububu/merge_pdf_sep/blob/master/sample2.png)  


