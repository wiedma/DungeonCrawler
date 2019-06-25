package animationeditor;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import engine.animation.Animation;
import engine.sprites.Sprite;

public class JPanelElementSprite extends JPanel implements MouseListener {
	
	private static final double SPRITE_SCALE = 1;
	
	private Animation animation;
	
	private Animationeditor animationeditor;
	
	private int spriteIndex;
	
	public JPanelElementSprite(Animationeditor animationeditor, Animation animation, int spriteIndex, Sprite sprite) {
		this.animation = animation;
		this.spriteIndex = spriteIndex;
		this.animationeditor = animationeditor;

		if(sprite != null && sprite.getImage(SPRITE_SCALE) == null)
			sprite = null;
		
		this.addMouseListener(this);
		
		this.setMaximumSize(new Dimension(Integer.MAX_VALUE, sprite != null ? sprite.getImage(SPRITE_SCALE).getHeight(null) + 20 : 50));
		this.setBorder(BorderFactory.createTitledBorder(String.valueOf(spriteIndex)));
		
		GridBagLayout gridBagLayout = new GridBagLayout(); 
		this.setLayout(gridBagLayout);
		GridBagConstraints c = gridBagLayout.getConstraints(this);		
		c.gridy = 5;
		
		//JIcon of the Image?
		if(sprite != null) {
			c.gridx = 2;
			Icon icon = new ImageIcon(sprite.getImage(SPRITE_SCALE));
			JLabel lIcon = new JLabel(icon);
			
			this.add(lIcon, c);
		}
		
		c.gridx = 5;
		JButton bAnimationSelect = new JButton("<<");
		bAnimationSelect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {				
				animationeditor.extractSpriteFromCurrentSpritesheetInto(animation, spriteIndex);
			}
		});
		this.add(bAnimationSelect, c);
	}

	public void mouseClicked(MouseEvent e) {
		if(e.getButton() == MouseEvent.BUTTON3) {
			animationeditor.deleteSprite(this.animation, this.spriteIndex);
		}
	}
	public void mousePressed(MouseEvent e) {}
	public void mouseReleased(MouseEvent e) {}
	public void mouseEntered(MouseEvent e) {}
	public void mouseExited(MouseEvent e) {}
}
