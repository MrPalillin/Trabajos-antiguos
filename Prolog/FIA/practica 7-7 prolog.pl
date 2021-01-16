transporte(roma,200).
transporte(londres,150).
transporte(tunez,100).

precio_alojamiento(hotel,roma,200).
precio_alojamiento(hotel,londres,150).
precio_alojamiento(hotel,tunez,100).
precio_alojamiento(hostal,roma,150).
precio_alojamiento(hostal,londres,100).
precio_alojamiento(hostal,tunez,80).
precio_alojamiento(camping,roma,100).
precio_alojamiento(camping,londres,50).
precio_alojamiento(camping,tunez,50).

precio_total(Pais,Alojamiento,Noches,PrecioTotal):-
    transporte(Pais,PrecioTransporte),
    precio_alojamiento(Alojamiento,Pais,PrecioAlojamiento),
    PrecioTotal is PrecioTransporte+(Noches*PrecioAlojamiento).
