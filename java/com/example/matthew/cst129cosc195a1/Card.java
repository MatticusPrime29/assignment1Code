package com.example.matthew.cst129cosc195a1;

public class Card 
{
	private char suit;
	private char rank;
	
	private final static int CARD_IDS[][] = {
		{R.drawable.ca, R.drawable.c2, R.drawable.c3,
			R.drawable.c4, R.drawable.c5, R.drawable.c6,
			R.drawable.c7, R.drawable.c8, R.drawable.c9,
			R.drawable.ct, R.drawable.cj, R.drawable.cq, 
			R.drawable.ck }, 
		{R.drawable.da, R.drawable.d2, R.drawable.d3,
			R.drawable.d4, R.drawable.d5, R.drawable.d6,
			R.drawable.d7, R.drawable.d8, R.drawable.d9,
			R.drawable.dt, R.drawable.dj, R.drawable.dq, 
			R.drawable.dk },
		{R.drawable.ha, R.drawable.h2, R.drawable.h3,
			R.drawable.h4, R.drawable.h5, R.drawable.h6,
			R.drawable.h7, R.drawable.h8, R.drawable.h9,
			R.drawable.ht, R.drawable.hj, R.drawable.hq, 
			R.drawable.hk },
		{R.drawable.sa, R.drawable.s2, R.drawable.s3,
			R.drawable.s4, R.drawable.s5, R.drawable.s6,
			R.drawable.s7, R.drawable.s8, R.drawable.s9,
			R.drawable.st, R.drawable.sj, R.drawable.sq, 
			R.drawable.sk }
	};
	
	public Card( char suit, char rank )
	{
		this.suit = suit;
		this.rank = rank;
	}
	
	public int getValue()
	{
		int value = 10;
		
		if ( rank >= '2' && rank <= '9' )
		{
			value = rank - '0';
		}
		else if ( rank == 'a' )
		{
			value = 11;
		}
		return value;
	}
	
	public int getDrawableId()
	{

		//Clubs = 0, Diamonds = 1, Hearts = 2, Spades = 3
		int suitIndex = (suit == 'c') ? 0 : (suit =='d') ? 1 : (suit == 'h') ? 2 : 3;
		
		int rankIndex = 0;  //Ace
		if ( getValue() < 10 )
		{
			rankIndex = getValue() - 1;
		}
		else if ( rank == 't' )
		{
			rankIndex = 9;
		}
		else if ( rank == 'j')
		{
			rankIndex = 10;
		}
		else if ( rank == 'q' )
		{
			rankIndex = 11;
		}
		else if ( rank == 'k' )
		{
			rankIndex = 12;
		}
		
		return CARD_IDS[suitIndex][rankIndex];		
	}
}
