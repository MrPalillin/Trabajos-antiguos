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

	MPI_Scatterv(layer,gTam,gIni,MPI_FLOAT,part,tam,MPI_FLOAT,ROOT_RANK,MPI_COMM_WORLD);

	MPI_Gatherv(part,tam,MPI_FLOAT,layer,gTam,gIni,MPI_FLOAT,ROOT_RANK,MPI_COMM_WORLD);

	./client -u g24 -x 1r3CtRMN -q mpilb energy_mpi.c -n 4

	mpicc -O3 -I. energy_mpiCopia.c -o energyCopia -lm
