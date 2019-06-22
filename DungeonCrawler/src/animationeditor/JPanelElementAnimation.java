package animationeditor;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import engine.animation.Animation;

public class JPanelElementAnimation extends JPanel {
	
	private Animationeditor animationeditor;
	
	private Animation animation;
	
	private JTextField txtAnimationName;
	private JButton bAnimationNameSet;
	
	public JPanelElementAnimation(Animationeditor animationeditor, Animation animation, ButtonGroup groupRadioButtonDefault, boolean defaultAnimation) {
		this.animationeditor = animationeditor;
		this.animation = animation;
		
//		if(defaultAnimation)
//			System.out.println("Im default: " + animation.getName());
		
		this.setMaximumSize(new Dimension(Integer.MAX_VALUE, 60));
		this.setBorder(BorderFactory.createTitledBorder(this.animation.getName()));
		
		GridBagLayout gridBagLayout = new GridBagLayout(); 
		this.setLayout(gridBagLayout);
		GridBagConstraints c = gridBagLayout.getConstraints(this);		
		c.gridy = 5;
		
		//radio button for selecting default animation
		c.gridx = 0;
		JRadioButton radioDefault = new JRadioButton("default");
		if(defaultAnimation) {
			SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					radioDefault.setSelected(true);
				}
			});
		}
		radioDefault.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				animationeditor.setDefaultAnimation(animation);
			}
		});
		groupRadioButtonDefault.add(radioDefault);
		this.add(radioDefault, c);
		
		//text field for animation name
		c.gridx = 1;		
		txtAnimationName = new JTextField(15);
		txtAnimationName.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				bAnimationNameSet.doClick();
			}
		});
		this.add(txtAnimationName, c);
		
		c.gridx = 3;
		bAnimationNameSet = new JButton("Set");
		bAnimationNameSet.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setBorder(BorderFactory.createTitledBorder(txtAnimationName.getText()));
				animationeditor.renameAnimation(animation.getName(), txtAnimationName.getText());
			}
		});
		this.add(bAnimationNameSet, c);
		
		c.gridx = 5;
		JButton bAnimationSelect = new JButton(">>");
		bAnimationSelect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {				
				animationeditor.setSelectedAnimation(animation);
				animationeditor.redrawAnimation();
			}
		});
		this.add(bAnimationSelect, c);
		
		this.updateDisplay();
	}
	
	public void updateDisplay() {
		this.txtAnimationName.setText(animation.getName());
	}
	
	public Animation getAnimation() {
		return this.animation;
	}
}
