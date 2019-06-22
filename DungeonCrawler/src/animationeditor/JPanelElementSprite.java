package animationeditor;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import engine.animation.Animation;
import engine.sprites.Sprite;

public class JPanelElementSprite extends JPanel {
	
	private Animation animation;
	
	private Animationeditor animationeditor;
	
	public JPanelElementSprite(Animationeditor animationeditor, Animation animation, int index, Sprite img) {
		this.animation = animation;
		
		this.setMaximumSize(new Dimension(Integer.MAX_VALUE, 60));
		this.setBorder(BorderFactory.createTitledBorder(String.valueOf(index)));
		
		GridBagLayout gridBagLayout = new GridBagLayout(); 
		this.setLayout(gridBagLayout);
		GridBagConstraints c = gridBagLayout.getConstraints(this);		
		c.gridy = 5;
		
		//JIcon of the Image?
		c.gridx = 2;
		Icon icon = new ImageIcon(img.getImage(1));
		JLabel lIcon = new JLabel(icon);
		
		this.add(lIcon, c);
		
		
		c.gridx = 5;
		JButton bAnimationSelect = new JButton("<<");
		bAnimationSelect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {				
				animationeditor.extractSpriteFromCurrentSpritesheetInto(animation, index);
			}
		});
		this.add(bAnimationSelect, c);
	}
}
