#include<stdio.h>
#include<stdlib.h>
#include<mpi.h>

int main(int argc,char *argv[]){
	if(argc<2){
		exit(EXIT_FAILURE);
	}
	int rank,size;
	MPI_Init(&argc,&argv);
	MPI_Comm_size(MPI_COMM_WORLD,&size);
	MPI_Comm_rank(MPI_COMM_WORLD,&rank);

	int i,j,k,layer_size=atoi(argv[1]);

	int ini,tam,resto;
	int gIni[size],gTam[size];
	int gRank[size];
	for(i=0;i<size;i++){
		gIni[i]=0;
		gTam[i]=0;
	}

	tam=layer_size/size;
	ini=tam*rank;
	resto=layer_size%size;
	if(resto!=0){
		if(resto>=size-rank)
			tam++;
		if(rank>size-resto)
			ini=ini+resto-(size-rank);
	}

	for(i=0;i<size;i++)
		gRank[i]=i;

	for(i=0;i<size;i++){
		gIni[i]=tam*gRank[i];
		gTam[i]=tam;
	}
	if(resto!=0){
		for(j=size-1;j>size-resto-1;j--)
			gTam[j]++;
		for(j=size-1;j>size-resto;j--)
			gIni[j]=gIni[j]+resto-(size-j);
	}

	/*if(rank==0)
	for(i=0;i<size;i++)
		printf("[%d]Tamaño %d e inicio %d\n",rank,gTam[i],gIni[i]);
	*/

	int *layer=malloc(sizeof(int)*layer_size);
	int *layer_copy=malloc(sizeof(int)*layer_size);

	if(rank==0)
		for(i=0;i<layer_size;i++){
			layer[i]=1;
			layer_copy[i]=1;
		}


	MPI_Scatterv(layer,gTam,gIni,MPI_INT,layer_copy,tam,MPI_INT,0,MPI_COMM_WORLD);

	for(i=0;i<size;i++)
		if(rank==i)
			for(j=0;j<tam;j++){
				layer_copy[j]*=rank;
				printf("[%d]Indice local: %d, indice global: %d, valor %d\n",rank,j,j+ini,layer_copy[j]);
			}

	MPI_Barrier(MPI_COMM_WORLD);

	MPI_Gatherv(layer_copy,tam,MPI_INT,layer,gTam,gIni,MPI_INT,0,MPI_COMM_WORLD);

	if(rank==0)
		for(i=0;i<layer_size;i++)
			printf("Valor en %i: %i\n",i,layer[i]);

	return 0;
}