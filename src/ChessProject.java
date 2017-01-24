import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.Stack;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.border.Border;



public class ChessProject extends JFrame implements MouseListener, MouseMotionListener {
	JLayeredPane layeredPane;
	JPanel chessBoard;
	JLabel chessPiece;
	int xAdjustment;
	int yAdjustment;
	int startX;
	int startY;
	int initialX;
	int initialY;
	JPanel panels;
	JLabel pieces;
	Boolean validMove = false;
	Boolean success =false;
	Boolean myturn = true;


	public ChessProject(){
		Dimension boardSize = new Dimension(600, 600);

		//  Use a Layered Pane for this application
		layeredPane = new JLayeredPane();
		getContentPane().add(layeredPane);
		layeredPane.setPreferredSize(boardSize);
		layeredPane.addMouseListener(this);
		layeredPane.addMouseMotionListener(this);

		//Add a chess board to the Layered Pane 
		chessBoard = new JPanel();
		layeredPane.add(chessBoard, JLayeredPane.DEFAULT_LAYER);
		chessBoard.setLayout( new GridLayout(8, 8) );
		chessBoard.setPreferredSize( boardSize );
		chessBoard.setBounds(0, 0, boardSize.width, boardSize.height);

		for (int i = 0; i < 64; i++) {
			JPanel square = new JPanel( new BorderLayout() );
			chessBoard.add( square );

			int row = (i / 8) % 2;
			if (row == 0)
				square.setBackground( i % 2 == 0 ? Color.white : Color.gray );
			else
				square.setBackground( i % 2 == 0 ? Color.gray : Color.white );
		}

		// Setting up the Initial Chess board.
		for(int i=8;i < 16; i++){			
			pieces = new JLabel( new ImageIcon("WhitePawn.png") );
			panels = (JPanel)chessBoard.getComponent(i);
			panels.add(pieces);	        
		}
		pieces = new JLabel( new ImageIcon("WhiteRook.png") );
		panels = (JPanel)chessBoard.getComponent(0);
		panels.add(pieces);
		pieces = new JLabel( new ImageIcon("WhiteKnight.png") );
		panels = (JPanel)chessBoard.getComponent(1);
		panels.add(pieces);
		pieces = new JLabel( new ImageIcon("WhiteKnight.png") );
		panels = (JPanel)chessBoard.getComponent(6);
		panels.add(pieces);
		pieces = new JLabel( new ImageIcon("WhiteBishup.png") );
		panels = (JPanel)chessBoard.getComponent(2);
		panels.add(pieces);
		pieces = new JLabel( new ImageIcon("WhiteBishup.png") );
		panels = (JPanel)chessBoard.getComponent(5);
		panels.add(pieces);
		pieces = new JLabel( new ImageIcon("WhiteKing.png") );
		panels = (JPanel)chessBoard.getComponent(3);
		panels.add(pieces);
		pieces = new JLabel( new ImageIcon("WhiteQueen.png") );
		panels = (JPanel)chessBoard.getComponent(4);
		panels.add(pieces);
		pieces = new JLabel( new ImageIcon("WhiteRook.png") );
		panels = (JPanel)chessBoard.getComponent(7);
		panels.add(pieces);
		for(int i=48;i < 56; i++){			
			pieces = new JLabel( new ImageIcon("BlackPawn.png") );
			panels = (JPanel)chessBoard.getComponent(i);
			panels.add(pieces);	        
		}
		pieces = new JLabel( new ImageIcon("BlackRook.png") );
		panels = (JPanel)chessBoard.getComponent(56);
		panels.add(pieces);
		pieces = new JLabel( new ImageIcon("BlackKnight.png") );
		panels = (JPanel)chessBoard.getComponent(57);
		panels.add(pieces);
		pieces = new JLabel( new ImageIcon("BlackKnight.png") );
		panels = (JPanel)chessBoard.getComponent(62);
		panels.add(pieces);
		pieces = new JLabel( new ImageIcon("BlackBishup.png") );
		panels = (JPanel)chessBoard.getComponent(58);
		panels.add(pieces);
		pieces = new JLabel( new ImageIcon("BlackBishup.png") );
		panels = (JPanel)chessBoard.getComponent(61);
		panels.add(pieces);
		pieces = new JLabel( new ImageIcon("BlackKing.png") );
		panels = (JPanel)chessBoard.getComponent(59);
		panels.add(pieces);
		pieces = new JLabel( new ImageIcon("BlackQueen.png") );
		panels = (JPanel)chessBoard.getComponent(60);
		panels.add(pieces);
		pieces = new JLabel( new ImageIcon("BlackRook.png") );
		panels = (JPanel)chessBoard.getComponent(63);
		panels.add(pieces);		
	}

	/*
		This method checks if there is a piece present on a particular square.
	 */
	private Boolean piecePresent(int x, int y){
		Component c = chessBoard.findComponentAt(x, y);
		if(c instanceof JPanel){
			return false;
		}
		else{
			return true;
		}
	}

	/*
		This is a method to check if a piece is a Black piece.
	 */
	private Boolean checkWhiteOponent(int newX, int newY){
		Boolean oponent;
		Component c1 = chessBoard.findComponentAt(newX, newY);
		JLabel awaitingPiece = (JLabel)c1;
		String tmp1 = awaitingPiece.getIcon().toString();			
		if(((tmp1.contains("Black")))){
			oponent = true;
		}
		else{
			oponent = false; 
		}		
		return oponent;
	}	

	private Boolean checkBlackOponent(int newX, int newY){
		Boolean oponent;
		Component c1 = chessBoard.findComponentAt(newX, newY);
		JLabel awaitingPiece = (JLabel)c1;
		String tmp1 = awaitingPiece.getIcon().toString();			
		if(((tmp1.contains("White")))){
			oponent = true;
		}
		else{
			oponent = false; 
		}		
		return oponent;
	}	


	public void mousePressed(MouseEvent e){
		chessPiece = null;
		Component c =  chessBoard.findComponentAt(e.getX(), e.getY());
		if (c instanceof JPanel) 
			return;

		Point parentLocation = c.getParent().getLocation();
		xAdjustment = parentLocation.x - e.getX();
		yAdjustment = parentLocation.y - e.getY();
		chessPiece = (JLabel)c;
		initialX = e.getX();
		initialY = e.getY();
		startX = (e.getX()/75);
		startY = (e.getY()/75);
		chessPiece.setLocation(e.getX() + xAdjustment, e.getY() + yAdjustment);
		chessPiece.setSize(chessPiece.getWidth(), chessPiece.getHeight());
		layeredPane.add(chessPiece, JLayeredPane.DRAG_LAYER);
	}

	public void mouseDragged(MouseEvent me) {
		if (chessPiece == null) return;
		chessPiece.setLocation(me.getX() + xAdjustment, me.getY() + yAdjustment);
	}


	public void mouseReleased(MouseEvent e) {
		if(chessPiece == null) return;

		chessPiece.setVisible(false);

		Component c =  chessBoard.findComponentAt(e.getX(), e.getY());
		String tmp = chessPiece.getIcon().toString();
		String pieceName = tmp.substring(0, (tmp.length()-4));

		
		if(myturn){
			if(pieceName.contains("Pawn")){
				pawnMove(pieceName, e);
			} else if (pieceName.contains("Knight")){
				knightMove(pieceName, e);
			}else if(pieceName.contains("Bishup")){
				bishupMove(pieceName, e);
			}else if(pieceName.contains("Rook")){
				rookMove(pieceName, e);
			}else if(pieceName.contains("Queen")){
				queenMove(pieceName, e);
			}else if(pieceName.contains("King")){
				kingMove(pieceName, e);
			}			
		}

		if(!validMove){		
			int location=0;
			if(startY ==0){
				location = startX;
			}
			else{
				location  = (startY*8)+startX;
			}
			String pieceLocation = pieceName+".png"; 
			pieces = new JLabel( new ImageIcon(pieceLocation) );
			panels = (JPanel)chessBoard.getComponent(location);
			panels.add(pieces);			
		}
		else{
			if(success){
				int location = 56 + (e.getX()/75);
				if (c instanceof JLabel){
					Container parent = c.getParent();
					parent.remove(0);
					pieces = new JLabel( new ImageIcon("WhiteQueen.png") );
					parent = (JPanel)chessBoard.getComponent(location);
					parent.add(pieces);			
				}
				else{
					Container parent = (Container)c;
					pieces = new JLabel( new ImageIcon("WhiteQueen.png") );
					parent = (JPanel)chessBoard.getComponent(location);
					parent.add(pieces);	            	
				}
			}
			else{
				if (c instanceof JLabel){
					Container parent = c.getParent();
					parent.remove(0);
					parent.add( chessPiece );
				}
				else {
					Container parent = (Container)c;
					parent.add( chessPiece );
				}
				chessPiece.setVisible(true);									
			}
		}
	}

	public void pawnMove(String pieceName, MouseEvent e){
		if(startY == 1)
		{
			if((startX == (e.getX()/75))&&((((e.getY()/75)-startY)==1)||((e.getY()/75)-startY)==2))
			{
				if((((e.getY()/75)-startY)==2)){
					if((!piecePresent(e.getX(), (e.getY())))&&(!piecePresent(e.getX(), (e.getY()+75)))){
						validMove = true;					
					}
					else{
						validMove = false;
					}							
				}
				else{
					if((!piecePresent(e.getX(), (e.getY()))))
					{
						validMove = true;					
					}	
					else{
						validMove = false;
					}													
				}
			}
			else{
				validMove = false;					
			}
		}
		else{
			int newY = e.getY()/75;
			int newX = e.getX()/75;				
			if((startX-1 >=0)||(startX +1 <=7))
			{
				if((piecePresent(e.getX(), (e.getY())))&&((((newX == (startX+1)&&(startX+1<=7)))||((newX == (startX-1))&&(startX-1 >=0)))))
				{
					if(pieceName.contains("White")){
						if(checkWhiteOponent(e.getX(), e.getY())){
							validMove = true;
						}
						else{
							validMove = false;
						}
					}
					else{
						if(checkBlackOponent(e.getX(), e.getY())){
							validMove = true;
						}
						else{
							validMove = false;
						}
					}
				}
				else{
					if(!piecePresent(e.getX(), (e.getY()))){
						if((startX == (e.getX()/75))&&((e.getY()/75)-startY)==1){
							if(startY == 6){
								success = true;
							}
							validMove = true;
						}
						else{
							validMove = false;
						}				
					}
					else{
						validMove = false;	
					}
				}
			}
			else{
				validMove = false;
			}				
		}		
	}

	public void knightMove(String pieceName, MouseEvent e){
		int newX = e.getX()/75;
		int newY = e.getY()/75;
		if(((newX < 0) || (newX > 7))||((newY < 0)||(newY > 7))){
			validMove = false;            
		}
		else{
			if(((newX == startX+1) && (newY == startY+2))||((newX == startX-1) && (newY == startY+2))||((newX == startX+2) && (newY == startY+1))||((newX == startX-2) && (newY == startY+1))||((newX == startX+1) && (newY == startY-2))||((newX == startX-1) && (newY == startY-2))||((newX == startX+2) && (newY == startY-1))||((newX == startX-2) && (newY == startY-1))){

				if(piecePresent(e.getX(), (e.getY()))){
					if(pieceName.contains("White")){
						if(checkWhiteOponent(e.getX(), e.getY())){
							validMove = true;
						}
						else{
							validMove = false;
						}
					}
					                                    
				}
				else{
					validMove = true;
				}
			}
			else{
				validMove = false;            
			}

		}
	}

	public void bishupMove(String pieceName, MouseEvent e){
		int newY = e.getY()/75;
		int newX = e.getX()/75;
		boolean inTheWay = false;
		int distance = Math.abs(startX-newX);
		if(((newX < 0) || (newX > 7))||((newY < 0)||(newY > 7))){
			validMove = false;
		}
		else{
			validMove = true;
			if(Math.abs(startX-newX)==Math.abs(startY-newY))
			{
				if((startX-newX < 0)&&(startY-newY < 0)){
					for(int i=0; i < distance;i++){
						if(piecePresent((initialX+(i*75)), (initialY+(i*75)))){
							inTheWay = true;
						}
					}
				}
				else if((startX-newX < 0)&&(startY-newY > 0)){
					for(int i=0; i < distance;i++){
						if(piecePresent((initialX+(i*75)), (initialY-(i*75)))){
							inTheWay = true;
						}
					}
				}
				else if((startX-newX > 0)&&(startY-newY > 0)){
					for(int i=0; i < distance;i++){
						if(piecePresent((initialX-(i*75)), (initialY-(i*75)))){
							inTheWay = true;
						}
					}
				}
				else if((startX-newX > 0)&&(startY-newY < 0)){
					for(int i=0; i < distance;i++){
						if(piecePresent((initialX-(i*75)), (initialY+(i*75)))){
							inTheWay = true;
						}
					}
				}
				if(inTheWay){
					validMove = false;
				}
				else{
					if(piecePresent(e.getX(), (e.getY()))){
						if(pieceName.contains("White")){
							if(checkWhiteOponent(e.getX(), e.getY())){
								validMove = true;
							}
							else{
								validMove = false;
							}
						}
						
					}
					else{
						validMove = true;
					}
				}
			}
			else{ // the move that is being tried is not a diagonal move...
				validMove = false;
			}
		}
	}

	public void rookMove(String pieceName, MouseEvent e){
		int newY = e.getY()/75;
		int newX = e.getX()/75;
		boolean intheway=false;

		if(((newX < 0)|| (newX > 7))||((newY < 0)||(newY > 7))){
			validMove = false;
		}

		else{
			if(((Math.abs(startX-newX)!=0)&&(Math.abs(startY-newY) == 0))|| ((Math.abs(startX-newX)==0)&&(Math.abs(newY-startY)!=0)))
			{
				if(Math.abs(startX-newX)!=0){
					//we have movement along the x axis
					int xMovement = Math.abs(startX-newX);
					if(startX-newX > 0){
						//movement in the left direction....
						for(int i=0;i < xMovement;i++){
							if(piecePresent(initialX-(i*75), e.getY())){
								intheway = true;
								break;
							}
							else{
								intheway = false;
							}
						}
					}
					else{
						for(int i=0;i < xMovement;i++){
							if(piecePresent(initialX+(i*75), e.getY())){
								intheway = true;
								break;
							}
							else{
								intheway = false;
							}
						}
					}
				}

				else{
					//we have movement along the y axis
					int yMovement = Math.abs(startY-newY);
					if(startY-newY > 0){
						//movement in the left direction....
						for(int i=0;i < yMovement;i++){
							if(piecePresent(e.getX(),initialY-(i*75))){
								intheway = true;
								break;
							}
							else{
								intheway = false;
							}
						}
					}
					else{
						for(int i=0;i < yMovement;i++){
							if(piecePresent(e.getX(),initialY+(i*75))){
								intheway = true;
								break;
							}
							else{
								intheway = false;
							}
						}
					}
				}
				if(intheway){
					validMove = false;
				}

				else{
					if(piecePresent(e.getX(), (e.getY()))){
						if(pieceName.contains("White")){
							if(checkWhiteOponent(e.getX(), e.getY())){
								validMove = true;
							}
							else{
								validMove = false;
							}
						}
						
					}
					else{
						validMove = true;
					}
				}
			}
			else
				validMove = false;

		}
	}

	public void kingMove(String pieceName, MouseEvent e){
		int newY = e.getY()/75;
		int newX = e.getX()/75;				
		if((startX-1 >=0)||(startX +1 <=7))
		{
			if((piecePresent(e.getX(), (e.getY())))&&((((newX == (startX+1)&&(startX+1<=7)))||((newX == (startX-1))&&(startX-1 >=0)))))
			{
				if(checkWhiteOponent(e.getX(), e.getY())){
					validMove = true;
					if(startY == 6){
						success = true;
					}						
				}
				else{
					validMove = false;
				}
			}
			else{
				if(!piecePresent(e.getX(), (e.getY()))){
					if((startX == (e.getX()/75))&&((e.getY()/75)-startY)==1){
						if(startY == 6){
							success = true;
						}
						validMove = true;
					}
					else{
						validMove = false;
					}				
				}
				else{
					validMove = false;	
				}
			}
		}
		else{
			validMove = false;
		}		
	}

	public void queenMove(String pieceName, MouseEvent e){
		int newY = e.getY()/75;
		int newX = e.getX()/75;			
		boolean inTheWay = false;		
		int distance = Math.abs(startX-newX);


		if(((newX < 0) || (newX > 7))||((newY < 0)||(newY > 7))){
			validMove = false;			
		}
		else{
			/*
				The Queen can either move like the Rook or a Bishop...You already have this code working! 
			 */
			validMove = true;				

			//Check if the move is a diagonal move

			if(Math.abs(startX-newX)==Math.abs(startY-newY))
			{
				// If there are any pieces along the diagonal in the way the move cannot be made.
				if((startX-newX < 0)&&(startY-newY < 0)){
					for(int i=0; i < distance;i++){
						if(piecePresent((initialX+(i*75)), (initialY+(i*75)))){
							inTheWay = true;
						}
					}						
				}
				else if((startX-newX < 0)&&(startY-newY > 0)){
					for(int i=0; i < distance;i++){
						if(piecePresent((initialX+(i*75)), (initialY-(i*75)))){						
							inTheWay = true;
						}
					}						
				}
				else if((startX-newX > 0)&&(startY-newY > 0)){
					for(int i=0; i < distance;i++){
						if(piecePresent((initialX-(i*75)), (initialY-(i*75)))){
							inTheWay = true;
						}
					}						
				}
				else if((startX-newX > 0)&&(startY-newY < 0)){
					for(int i=0; i < distance;i++){
						if(piecePresent((initialX-(i*75)), (initialY+(i*75)))){
							inTheWay = true;
						}
					}						
				}										
				if(inTheWay){
					validMove = false;
				}
				else{
					if(piecePresent(e.getX(), (e.getY()))){
						if(pieceName.contains("White")){
							if(checkWhiteOponent(e.getX(), e.getY())){
								validMove = true;
							}
							else{
								validMove = false;
							}
						}
						else{
							if(checkBlackOponent(e.getX(), e.getY())){
								validMove = true;
							}
							else{
								validMove = false;
							}
						}												
					}
					else{
						validMove = true;
					}												
				}										
			}
			else if(((Math.abs(startX-newX)!=0)&&(Math.abs(startY-newY) == 0))|| ((Math.abs(startX-newX)==0)&&(Math.abs(newY-startY)!=0)))
			{
				if(Math.abs(startX-newX)!=0){
					//we have movement along the x axis
					int xMovement = Math.abs(startX-newX);
					if(startX-newX > 0){
						//movement in the left direction....
						for(int i=0;i < xMovement;i++){
							if(piecePresent(initialX-(i*75), e.getY())){
								inTheWay = true;
								break;
							}
							else{
								inTheWay = false;
							}
						}							
					}
					else{
						for(int i=0;i < xMovement;i++){								
							if(piecePresent(initialX+(i*75), e.getY())){
								inTheWay = true;
								break;
							}
							else{
								inTheWay = false;
							}
						}
					}
				}
				else{
					//we have movement along the y axis
					int yMovement = Math.abs(startY-newY);
					if(startY-newY > 0){
						//movement in the left direction....
						for(int i=0;i < yMovement;i++){									
							if(piecePresent(e.getX(),initialY-(i*75))){
								inTheWay = true;
								break;
							}
							else{
								inTheWay = false;
							}
						}							
					}
					else{
						for(int i=0;i < yMovement;i++){
							if(piecePresent(e.getX(),initialY+(i*75))){
								inTheWay = true;
								break;
							}
							else{
								inTheWay = false;
							}
						}
					}
				}
				if(inTheWay){
					validMove = false;
				}
				else{
					if(piecePresent(e.getX(), (e.getY()))){
						if(pieceName.contains("White")){
							if(checkWhiteOponent(e.getX(), e.getY())){
								validMove = true;
							}
							else{
								validMove = false;
							}
						}
						else{
							if(checkBlackOponent(e.getX(), e.getY())){
								validMove = true;
							}
							else{
								validMove = false;
							}
						}												
					}
					else{
						validMove = true;
					}
				}
			}				
			else{ // the move that is being tried is not a diagonal move...
				validMove = false;
			}				
		}	
	}

	public void computerMove(){
		
	}

	public void mouseClicked(MouseEvent e) {

	}
	public void mouseMoved(MouseEvent e) {
	}
	public void mouseEntered(MouseEvent e){

	}
	public void mouseExited(MouseEvent e) {

	}

	private Stack findWhitePieces(){    
		Stack squares = new Stack();
		String icon;
		int x;
		int y;
		String pieceName;
		/*
	        The loop firstly identifies where are the pieces are.
		 */	
		for(int i=0;i<600;i+=75){
			for(int j=0;j < 600;j+=75){
				y = i/75;
				x=j/75;
				Component tmp = chessBoard.findComponentAt(j, i);
				if(tmp instanceof JLabel){                    
					chessPiece = (JLabel)tmp;
					icon = chessPiece.getIcon().toString();
					pieceName = icon.substring(0, (icon.length()-4));
					if(pieceName.contains("White")){
						Square stmp = new Square(x, y, pieceName);
						squares.push(stmp);
					}
				}
			}
		}
		return squares;
	}
	private Stack getWhiteAttckingSquares(Stack pieces){
		Stack knight = new Stack();
		while(!pieces.empty()){
			Square s = (Square)pieces.pop();
			String tmpString = s.getName();
			if(tmpString.contains("Knight")){
				Stack tempK = getKnightMoves(s.getXC(), s.getYC(), s.getName());
				while(!tempK.empty()){
					Square tempKnight = (Square)tempK.pop();
					knight.push(tempKnight);
				}
			}
			else if(tmpString.contains("Bishop")){
			}

		}
		return knight;

	}

	/*
        Method to color a number of squares...
	 */
	private void colorSquares(Stack squares){
		Border greenBorder = BorderFactory.createLineBorder(Color.GREEN,3);        
		while(!squares.empty()){
			Square s = (Square)squares.pop();
			int location = s.getXC() + ((s.getYC())*8);                        
			JPanel panel = (JPanel)chessBoard.getComponent(location);
			panel.setBorder(greenBorder);            
		}        
	}

	private Stack getKnightMoves(int x, int y, String piece){
		Stack moves = new Stack();
		Stack attacking = new Stack();
		/*
	            Essentially the knight can move in L shapes, so if we take in any square (x, y)
	            the following list is the complete possible movements:
	            (x+1, y+2), (x+1, y-2),(x-1, y+2), (x-1, y-2)
	            (x+2, y+1), (x+2, y-1),(x-2, y+1), (x-1, y-1)
	       
		 */
		Square s = new Square(x+1, y+2);                
		moves.push(s);        
		Square s1 = new Square(x+1, y-2);
		moves.push(s1);
		Square s2 = new Square(x-1, y+2);
		moves.push(s2);
		Square s3 = new Square(x-1, y-2);
		moves.push(s3);
		Square s4 = new Square(x+2, y+1);
		moves.push(s4);
		Square s5 = new Square(x+2, y-1);
		moves.push(s5);
		Square s6 = new Square(x-2, y+1);
		moves.push(s6);
		Square s7 = new Square(x-2, y-1);
		moves.push(s7);        
		for(int i=0;i < 8;i++){
			Square tmp = (Square)moves.pop();
			if((tmp.getXC() < 0)||(tmp.getXC() > 7)||(tmp.getYC() < 0)||(tmp.getXC() > 7)){
			}
			else if(piecePresent(((tmp.getXC()*75)+20), (((tmp.getYC()*75)+20)))){
				if(piece.contains("White")){
					if(checkWhiteOponent(((tmp.getXC()*75)+20), ((tmp.getYC()*75)+20))){
						attacking.push(tmp);
					}
					else{
						System.out.println("Its our own piece");
					}
				}
				else{
					if(checkBlackOponent(tmp.getXC(), tmp.getYC())){
						attacking.push(tmp);
					}
				}
			}
			else{
				attacking.push(tmp);
			}                        
		}
		Stack tmp = attacking;
		colorSquares(tmp);
		return attacking;
	}
	/*
		Main method that gets the ball moving.
	 */
	public static void main(String[] args) {
		JFrame frame = new ChessProject();
		frame.setDefaultCloseOperation(DISPOSE_ON_CLOSE );
		frame.pack();
		frame.setResizable(true);
		frame.setLocationRelativeTo( null );
		frame.setVisible(true);
	}
}


