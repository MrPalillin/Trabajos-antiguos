%{
#include "tipo.h"
%}
id 	[a-zA-Z\_][0-9a-zA-Z\_]*
longId	\"(\\.|[^"\\])*\"
%%
"graph"		{return GRAPH;}
[-][-]		{return EDGE;}
{longId}	{return LONGID;}
{id}		{return ID;}
[ \t\n]+	;
.		return yytext[0];
%%