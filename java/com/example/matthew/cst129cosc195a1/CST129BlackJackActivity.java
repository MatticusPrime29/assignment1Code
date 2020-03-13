package com.example.matthew.cst129cosc195a1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TableRow;
import android.widget.Toast;

/**
 * Matthew Martens
 * CST129
 */
public class CST129BlackJackActivity extends AppCompatActivity {

    TableRow cardsPlayer;
    TableRow cardsDealer;
    public final static int BLACKJACK = 21;
    private static final int DEALER_HOLD = 17;
    private final static int BET_AMOUNT = 50;
    private static final int POT_STARTING_AMOUNT = 500;
    EditText txtPot;
    Button btnDeal;
    Button btnHit;
    Button btnStay;
    Deck deck;
    Integer pot;
    Player dealer;
    Player human;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cst129_black_jack);

        txtPot = (EditText) findViewById(R.id.txtNumber);
        btnDeal = (Button) findViewById(R.id.btnDeal);
        btnHit = (Button) findViewById(R.id.btnHit);
        btnStay = (Button) findViewById(R.id.btnStay);

        //Instantiate players
        dealer = new Player();
        human = new Player();

        //Set button access
        btnDeal.setEnabled(true);
        btnHit.setEnabled(false);
        btnStay.setEnabled(false);

        //Disable the Text View so players can't edit
        txtPot.setEnabled(false);

        //Set the text of the pot
        pot = POT_STARTING_AMOUNT;
        txtPot.setText(String.format("$%d",pot));

        //Instantiate the viewable cards for players
        cardsPlayer = (TableRow) findViewById(R.id.rwPlayer);
        cardsDealer = (TableRow) findViewById(R.id.rwDealer);

        deck = new Deck();

        //Event handler for the Deal button
        btnDeal.setOnClickListener(v -> {

            dealHandler();

        });

        //Event handler for the Hit button
        btnHit.setOnClickListener(v -> {

            hitHandler();

        });

        //Event handler for the Stay button
        btnStay.setOnClickListener(v -> {

            stayHandler();

        });
    }

    /**
     * This method handles the Deal button functionality. It removes the views for the
     * players, adds the cards and views for the players, and sets the button access.
     */
    private void dealHandler()
    {
        cardsDealer.removeAllViews();
        cardsPlayer.removeAllViews();

        Card pCard1 = deck.getNextCard();
        Card pCard2 = deck.getNextCard();
        Card dCard1 = deck.getNextCard();
        Card dCard2 = deck.getNextCard();

        human.addCard(pCard1);
        human.addCard(pCard2);
        dealer.addCard(dCard1);
        dealer.addCard(dCard2);


        ImageView ivP1 = new ImageView( this );
        ivP1.setImageResource( R.drawable.b );
        cardsPlayer.addView( ivP1 );

        ImageView ivP2 = new ImageView( this );
        ivP2.setImageResource( pCard2.getDrawableId() );
        cardsPlayer.addView( ivP2 );

        ImageView ivD1 = new ImageView( this );
        ivD1.setImageResource( R.drawable.b );
        cardsDealer.addView( ivD1 );

        ImageView ivD2 = new ImageView( this );
        ivD2.setImageResource( dCard2.getDrawableId() );
        cardsDealer.addView( ivD2 );

        btnDeal.setEnabled(false);
        btnHit.setEnabled(true);
        btnStay.setEnabled(true);
    }

    /**
     * This method handles the Hit button functionality. It will add cards and the views
     * for the human players hand every time the button is clicked.
     */
    private void hitHandler()
    {
        Card pCardNext = deck.getNextCard();

//        playerHand.add(pCardNext);
        human.addCard(pCardNext);

        ImageView ivP3 = new ImageView(this);
        ivP3.setImageResource(pCardNext.getDrawableId());
        cardsPlayer.addView(ivP3);

    }

    /**
     * This method handles the Stay button functionality. For the most part it will handle the
     * results for each round. It calls the showCards, gameResults and addToDiscards methods. It will add more
     * cards and views to the dealers hand if below 17, compares the total scores of the human and dealer, displays
     * a Toast message for the result, sets a number called winCondition to be passed into the gameResults method,
     * and sets the button access once again.
     */
    private void stayHandler()
    {
        showCards();

        int winCondition;

        int dealerTotal = dealer.getScore();
        int playerTotal = human.getScore();

        //Adds card and view for the dealer while their amount is less than 17
        while(dealerTotal < DEALER_HOLD)
        {
            Card dCardNext = deck.getNextCard();
            dealer.addCard(dCardNext);
            ImageView ivDNext = new ImageView(this);
            ivDNext.setImageResource(dCardNext.getDrawableId());
            cardsDealer.addView(ivDNext);
            dealerTotal = dealer.getScore();
        }

        //Compares the total scores for each player and displays an appropriate message
        if((dealerTotal < playerTotal && playerTotal <= BLACKJACK) || (dealerTotal > BLACKJACK && playerTotal <= BLACKJACK))
        {
            Toast.makeText(getApplicationContext(),"Congratulations you win! Human: " + playerTotal + " Dealer: " + dealerTotal,Toast.LENGTH_LONG).show();
            winCondition = 1;
        }
        else
        {
            if((dealerTotal > playerTotal && dealerTotal <= BLACKJACK)||(playerTotal > BLACKJACK && dealerTotal <= BLACKJACK))
            {
                Toast.makeText(getApplicationContext(),"Sorry, you lose. Human: " + playerTotal + " Dealer: " + dealerTotal,Toast.LENGTH_LONG).show();
                winCondition = 2;
            }
            else
            {
                Toast.makeText(getApplicationContext(),"It's a tie. Human: " + playerTotal + " Dealer: " + dealerTotal,Toast.LENGTH_LONG).show();
                winCondition = 3;
            }
        }

        //Set button access
        btnDeal.setEnabled(true);
        btnHit.setEnabled(false);
        btnStay.setEnabled(false);

        gameResults(winCondition);
        addToDiscard();

    }

    /**
     * This method removes the views of the hidden cards and adds the views of the
     * original hidden cards drawn for the players
     */
    private void showCards()
    {
        cardsDealer.removeViewAt(0);
        cardsPlayer.removeViewAt(0);

        ImageView ivPOG = new ImageView(this);
        ivPOG.setImageResource(human.cards.get(0).getDrawableId());
        cardsPlayer.addView(ivPOG,0);

        ImageView ivDOG = new ImageView(this);
        ivDOG.setImageResource(dealer.cards.get(0).getDrawableId());
        cardsDealer.addView(ivDOG,0);

    }

    /**
     * This method goes through the players hands and adds the cards
     * from the round in the discard collection.
     */
    private void addToDiscard()
    {
        for(Card humanCard : human.removeCards())
        {
            deck.addToDiscard(humanCard);
        }
        for(Card dealerCard : dealer.removeCards())
        {
            deck.addToDiscard(dealerCard);
        }
    }

    /**
     * This method sets the pot amount for the human depending on the winCondition set in
     * stayHandler method. It will set the text according to the amount.
     * @param winCondition
     */
    private void gameResults(int winCondition)
    {
        switch(winCondition)
        {
            case 1:
            {
                pot += BET_AMOUNT;
                break;
            }
            case 2:
            {
                pot -= BET_AMOUNT;
                break;
            }
            default:
            {
                break;
            }
        }
        txtPot.setText(String.format("$%d",pot));
    }
}
