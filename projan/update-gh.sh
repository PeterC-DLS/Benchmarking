
 echo -e "Starting to update Benchmarking working-copy\n"
#moving files created in build into home
#moving scripts to be run into home
 # cp -R testTen $HOME
 # cp -R refBench $HOME

 while getopts "f:" opt; do
   case $opt in
     f)
       cp -R $OPTARG $HOME
       ;;
     l)
       ;;
   esac
 done

  cp -R projan/normFromRef.py $HOME
  cp -R projan/timePlot.py $HOME
  cp -R projan/plotWithSize.py $HOME

  cd $HOME
  git config --global user.email "travis@travis-ci.org"
  git config --global user.name "Travis"
  #cloning the relevant repo
  git clone --quiet --branch=working-copy https://${TOKEN}@github.com/Esmae/Benchmarking.git  Benchmarking> /dev/null
  
#normalising the benchmarking data just collected
#echo -e "calling normFromRef"
#python normFromRef.py


#attaching the time and date to the data files
 # today=$(date +%Y-%m-%d_%H-%M)
 # mv testTen testTen."$today"
 # cp Benchmarking/projan/normData/testTenNorm Benchmarking/projan/normData/testTenNorm."$today" 
   
  
#putting the files in local repo
#  cp -Rf testTen* Benchmarking/projan/data
#  cp -Rf refBench Benchmarking/projan
   cp -Rf testOrig Benchmarking/projan  
   cp -Rf testMine Benchmarking/projan
   cp -Rf testIndex Benchmarking/projan

OPTIND=1

 while getopts "f:" opt; do
   case $opt in
     f)
       cp -Rf $OPTARG Benchmarking/projan
       ;;
     l)
       ;;
   esac
 done
  
 ARRAYFILE=()
 ARRAYLABEl=()
 COUNTFILE=0
 COUNTLABEL=0
 OPTIND=1
 while getopts "f:l:" opt; do
    case $opt in
      f)
        ARRAYFILE[$COUNTFILE]=$OPTARG
        COUNTFILE=$(($COUNTFILE + 1))
        ;;
      l)
        ARRAYLABEL[$COUNTLABEL]=$OPTARG
        COUNTLABEL=$(($COUNTLABEL + 1))
        ;;
    esac
 done
     
 echo ${ARRAYFILE[*]}
 echo ${ARRAYLABEL[*]}
    
        
  
  
  #calls the python script that creates the 'with time' plot
  #echo -e "calling timePlot"
  #python timePlot.py
  
 # plotting the throughput score against different sizes of dataset
 python plotWithSize.py "${ARRAYFILE[*]}" "${ARRAYLABEL[*]}" "my_title" 

  #putting the new 'with time' plot in the repo (overwritting the latest one)
  #cp -Rf TimePlot.png Benchmarking/projan/figures
  #cp -Rf TimePlotTen.png Benchmarking/projan/figures
  
  cd Benchmarking
 #adding the new files and changing files so they are ready to commit 
  git add -f --ignore-removal .
#commiting the added changes
  git commit -m "Travis build pushed to Benchmarking working-copy [skip ci]"
#pushes the changes the github on the working-copy branch of Benchmarking
  git push -fq https://${TOKEN}@github.com/Esmae/Benchmarking.git working-copy > /dev/null

  echo -e "Success? \n"

