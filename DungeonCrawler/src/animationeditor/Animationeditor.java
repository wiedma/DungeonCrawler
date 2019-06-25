package animationeditor;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.HashMap;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;

import engine.animation.Animation;
import engine.io.AnimationLoader;
import engine.sprites.Sprite;
import engine.sprites.Spritesheet;
import engine.window.KeyRegister;

public class Animationeditor extends JFrame {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8810485022803061065L;

	public static void main(String[] args) {
		new Animationeditor();
	}
	
	private HashMap<String, Animation> animationMapActive;
	private Class<?> animationMapRequester;
	
	private JTextField txtTopBarSave;
	private JButton bTopBarSave;
	
	private JPanel pAnimationMap;
	private ButtonGroup groupRadioButtonDefault = new ButtonGroup();
	
	private Animation animationSelected;
	private JLabel lAnimationWrapperTopBarAnimationName;
	private JPanel pAnimation;

	private JPanel pSpritesheetChooserWrapper;
	private JPanel pSpritesheetChooser;
	private SpritesheetSelector spritesheetSelectorActive;
	
	public Animationeditor() {
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.addKeyListener(KeyRegister.getKeyRegister());
		this.initJFrameStructure();
		
		//create empty scene
		loadAnimationMap(new HashMap<String, Animation>(), false);
		
//		this.pack();
		this.setSize(1150, 500);
		this.setLocationRelativeTo(null);
		this.setVisible(true);	
	}
	
	private void initJFrameStructure() {
		//the top bar for loading and saving the AnimationSet
		JPanel pTopBar = new JPanel();
			JPanel pTopBarLoading = new JPanel();
			pTopBarLoading.setBorder(BorderFactory.createTitledBorder("Load AnimationSet"));
				JLabel lTxtTopBarLoad = new JLabel("Class:");
				pTopBarLoading.add(lTxtTopBarLoad);
			
				JTextField txtTopBarLoad = new JTextField(15);
				txtTopBarLoad.addActionListener(new ActionListener() {					
					public void actionPerformed(ActionEvent e) {
						try {
							animationMapRequester = Class.forName(txtTopBarLoad.getText());
							txtTopBarSave.setText(txtTopBarLoad.getText());
							animationMapActive = AnimationLoader.forceLoadAnimations(animationMapRequester); //TODO the default system doesnt yet work completely => tick a different animation as default and reload, it will stay as you ticked it!
							System.out.println(animationMapActive.get("default").getName());
							setTitle(txtTopBarLoad.getText());
							redrawAnimationMap();
						}catch(Exception exc) {
							exc.printStackTrace();
						}
					}
				});
				pTopBarLoading.add(txtTopBarLoad);
			pTopBar.add(pTopBarLoading);
			
			JPanel pTopBarSaving = new JPanel();
			pTopBarSaving.setBorder(BorderFactory.createTitledBorder("Save"));
			
				txtTopBarSave = new JTextField(15);
				txtTopBarSave.addActionListener(new ActionListener() {					
					public void actionPerformed(ActionEvent e) {
						bTopBarSave.doClick();
					}
				});
				pTopBarSaving.add(txtTopBarSave);
			
				bTopBarSave = new JButton("Save");
				bTopBarSave.addActionListener(new ActionListener() {					
					public void actionPerformed(ActionEvent e) {
						try {
							animationMapRequester = Class.forName(txtTopBarSave.getText());
							if(animationMapRequester == null) {
								System.err.println("The class '" + txtTopBarSave.getText() + "'does not exist");
								return;
							}
							AnimationLoader.writeAnimations(animationMapRequester, animationMapActive);
						}catch(Exception exc) {
							exc.printStackTrace();
						}
					}
				});
				pTopBarSaving.add(bTopBarSave);
			pTopBar.add(pTopBarSaving);
		this.add(pTopBar, BorderLayout.NORTH);
		
		
		
		//the first JSplitPane divides the AnimationMap and Animation area from the Spritesheet Display on the right
		JSplitPane splitMain = new JSplitPane();
		splitMain.setResizeWeight(2/3d);
		splitMain.addKeyListener(KeyRegister.getKeyRegister());
		splitMain.setContinuousLayout(true);
			//AnimationMap & Animation
			JSplitPane splitAnimation = new JSplitPane();
			splitAnimation.setResizeWeight(0.5);
			splitAnimation.addKeyListener(KeyRegister.getKeyRegister());
			splitAnimation.setContinuousLayout(true);
				//AnimationMap
				JPanel pAnimationMapWrapper = new JPanel(new BorderLayout());
					pAnimationMap = new JPanel();
					 BoxLayout boxAnimationMap = new BoxLayout(pAnimationMap, BoxLayout.Y_AXIS);
					 pAnimationMap.setLayout(boxAnimationMap);				
					 JScrollPane scrollAnimationMap = new JScrollPane(pAnimationMap);
					 scrollAnimationMap.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
					 scrollAnimationMap.getVerticalScrollBar().setUnitIncrement(20);					
					pAnimationMapWrapper.add(scrollAnimationMap);
					
					JPanel pAnimationMapAddAnimation = new JPanel();
						JButton bAnimationMapAddAnimation = new JButton("Add new Animation");
						bAnimationMapAddAnimation.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent e) {
								//check if there is a Animation of name default
								if(checkForAnimationsOfNameDefault())
									return;
								//get title that not used yet
								int title = -1;
								boolean titleCollides = true;
								while(titleCollides) {
									//check if it already exists
									titleCollides = false;
									title++;
									for(String key : animationMapActive.keySet()) {
										if(key.equals(String.valueOf(title))) {
											titleCollides = true;
											break;
										}
									}
								}
								animationMapActive.put(String.valueOf(title), new Animation(String.valueOf(title), new Sprite[0], 0.0, true, ""));
								redrawAnimationMap();
							}
						});
						pAnimationMapAddAnimation.add(bAnimationMapAddAnimation);
					pAnimationMapWrapper.add(pAnimationMapAddAnimation, BorderLayout.SOUTH);
					pAnimationMapWrapper.add(new JScrollPane(pAnimationMapAddAnimation, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER), BorderLayout.SOUTH);
				splitAnimation.setLeftComponent(pAnimationMapWrapper);
				
				
				
				
				//Sprite Overview				
				JPanel pAnimationWrapper = new JPanel(new BorderLayout());
				
					//display name of animation
					JPanel pAnimationWrapperTopBar = new JPanel();
						lAnimationWrapperTopBarAnimationName = new JLabel();
						pAnimationWrapperTopBar.add(lAnimationWrapperTopBarAnimationName);
					pAnimationWrapper.add(pAnimationWrapperTopBar, BorderLayout.NORTH);
				
					pAnimation = new JPanel();
					BoxLayout boxPAnimation = new BoxLayout(pAnimation, BoxLayout.Y_AXIS);
					pAnimation.setLayout(boxPAnimation);
						JScrollPane scrollSprites = new JScrollPane(pAnimation);
						scrollSprites.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
						scrollSprites.getVerticalScrollBar().setUnitIncrement(20);					
					pAnimationWrapper.add(scrollSprites, BorderLayout.CENTER);
					
					JPanel pAnimationAddSprite = new JPanel();
						JButton bAnimationAddSprite = new JButton("Add new Sprite");
						bAnimationAddSprite.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent e) {
								if(animationSelected == null) {
									System.err.println("Please select a animation before you try to add a new sprite");
									return;
								}
								Sprite[] spritesNew = new Sprite[animationSelected.getSprites().length + 1];
								for(int i = 0; i < animationSelected.getSprites().length; i++)
									spritesNew[i] = animationSelected.getSprites()[i];
								
								spritesNew[spritesNew.length-1] = null;
								
								animationSelected.setSprites(spritesNew);
								
								redrawAnimation();
							}
						});
						pAnimationAddSprite.add(bAnimationAddSprite);						
					pAnimationWrapper.add(pAnimationAddSprite, BorderLayout.SOUTH);
				splitAnimation.setRightComponent(pAnimationWrapper);
				//Animation				
			splitMain.setLeftComponent(splitAnimation);
			
			
			
			//Spritesheet Display
			pSpritesheetChooserWrapper = new JPanel(new BorderLayout());
				JPanel pSpritesheetChooserTopBar = new JPanel();
				pSpritesheetChooserTopBar.setBorder(BorderFactory.createTitledBorder("Load Spritesheet"));
					GridBagLayout gridBagLayoutSpritesheetChooserTopBar = new GridBagLayout();
					pSpritesheetChooserTopBar.setLayout(gridBagLayoutSpritesheetChooserTopBar);
					GridBagConstraints gridBagConstraintsSpritesheetChooserTopBar = gridBagLayoutSpritesheetChooserTopBar.getConstraints(pSpritesheetChooserTopBar);
					gridBagConstraintsSpritesheetChooserTopBar.gridy = 1;
					
					gridBagConstraintsSpritesheetChooserTopBar.gridx = 0;
					JLabel lTxtLoadSpritesheet = new JLabel(Spritesheet.DIR_SPRITESHEETS + "/");
					pSpritesheetChooserTopBar.add(lTxtLoadSpritesheet, gridBagConstraintsSpritesheetChooserTopBar);
					
					gridBagConstraintsSpritesheetChooserTopBar.gridy = 2;
					gridBagConstraintsSpritesheetChooserTopBar.gridx = 0;
					JTextField txtLoadSpritesheet = new JTextField(15);
					txtLoadSpritesheet.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							loadSpritesheet(new Spritesheet(new File(Spritesheet.DIR_SPRITESHEETS + "/" + txtLoadSpritesheet.getText())));
						}
					});
					pSpritesheetChooserTopBar.add(txtLoadSpritesheet, gridBagConstraintsSpritesheetChooserTopBar);
				pSpritesheetChooserWrapper.add(pSpritesheetChooserTopBar, BorderLayout.NORTH);
				
				pSpritesheetChooser = new JPanel();
				pSpritesheetChooserWrapper.add(pSpritesheetChooser, BorderLayout.CENTER);
			splitMain.setRightComponent(pSpritesheetChooserWrapper);
			
		this.add(splitMain, BorderLayout.CENTER);		
	}
	
	private void loadAnimationMap(HashMap<String, Animation> animationMap, boolean nullIsOK) {
		if(animationMap == null && !nullIsOK)
			return;
		this.animationMapActive = animationMap;
		
//		if(animationMap.keySet().size() > 0)
//			System.out.println(animationMap.get("default").getName());
		
		//filter out the default animation
		
		//load animation map
		for(String animationName : animationMap.keySet()) {
			if(animationName.equals("default") && animationMap.keySet().size() != 1)
				continue;
			
			pAnimationMap.add(new JPanelElementAnimation(
					this,
					animationMap.get(animationName),
					groupRadioButtonDefault,
					animationMap.get(animationName) == animationMap.get("default"),
					animationMap.get(animationName) == animationSelected
			));
		}
		
		//tick the radioDefaultButton of the Animation that's actually the default
		
	}
	
	public void redrawAnimationMap() {
		this.pAnimationMap.removeAll();
		
		this.loadAnimationMap(this.animationMapActive, true);
		
		//print name of current animation onto the top bar on the Aanimation view
		if(animationSelected != null)
			this.lAnimationWrapperTopBarAnimationName.setText(animationSelected.getName());
		
		this.pAnimationMap.revalidate();
		this.pAnimationMap.repaint();
	}
	
	private void loadAnimation(Animation animation, boolean nullIsOK) {
		if(animation == null && !nullIsOK)
			return;
		this.animationSelected = animation;
		
		//filter out the default animation
		
		//load animation map
		for(int spriteI = 0; spriteI < animation.getSprites().length; spriteI++) {
			pAnimation.add(new JPanelElementSprite(this, animation, spriteI, animation.getSprite(spriteI)));
		}
		
		if(animation.getSprites().length == 0) {
			pAnimation.add(new JLabel("<empty>"));
		}
		
		//tick the radioDefaultButton of the Animation that's actually the default
		
	}
	
	public void redrawAnimation() {
		this.pAnimation.removeAll();
		
		this.loadAnimation(this.animationSelected, true);
		
		//print name of current animation onto the top bar on the Aanimation view
		if(animationSelected != null)
			this.lAnimationWrapperTopBarAnimationName.setText(animationSelected.getName());
		
		this.pAnimation.revalidate();
		this.pAnimation.repaint();
	}
	
	public void renameAnimation(String animationBefore, String animationAfter) {
		if(animationAfter.equals("default")) {
			System.err.println("You cannot give an Animation the name 'default' as it is used to mark the default Animation of a GameObject.\n"
					+ "To mark an Animation to be the default, please tick the 'default' button on its left");
			this.redrawAnimationMap();
			return;
		}
		Animation animation = animationMapActive.get(animationBefore);
		if(!animationBefore.equals("default"))
			animationMapActive.remove(animationBefore);
		animationMapActive.put(animationAfter, animation);
		animation.setName(animationAfter);
		this.redrawAnimationMap();
	}
	
	/**
	 * @return returns true if and only if there is a Animation in the currently active AnimationMap that comes by the name of 'default' 
	 */
	private boolean checkForAnimationsOfNameDefault() {
		boolean defaultAnimationFound = false;
		for(String key : this.animationMapActive.keySet()) {
			if(this.animationMapActive.get(key).getName().equals("default")) {
				defaultAnimationFound = true;
				break;
			}
		}
		if(!defaultAnimationFound)
			return false;
		System.err.println("There is a Animation of name 'default' please rename that Animation first, before you continue using the Animationeditor\n"
				+ "You can specify a Animation to be the default one by ticking the 'default'-radio box on the left side of it");
		return true;
	}
	
	public void loadSpritesheet(Spritesheet spritesheet) {
		this.pSpritesheetChooser.removeAll();
		
		this.pSpritesheetChooser.setLayout(new BorderLayout());
		try {
			this.spritesheetSelectorActive = new SpritesheetSelector(this, spritesheet); 
			this.pSpritesheetChooser.add(this.spritesheetSelectorActive, BorderLayout.CENTER);
		}catch(Exception e) {}
		
		this.pSpritesheetChooserWrapper.revalidate();
	}
	
	public boolean isSpritesheetChooserStillLoaded(SpritesheetSelector spritesheetSelector) {
		return pSpritesheetChooser.isAncestorOf(spritesheetSelector);
	}
	
	public void extractSpriteFromCurrentSpritesheetInto(Animation animation, int spriteIndex) {
		if(this.spritesheetSelectorActive == null)
			return;
		
		Sprite sprite = this.spritesheetSelectorActive.extractSelectedSprite();
		if(sprite == null)
			return;
		
		animation.getSprites()[spriteIndex] = sprite;
		redrawAnimation();
	}
	
	public void deleteAnimation(Animation animation) {
		
		if(this.animationMapActive.get("default") == animation)
			this.animationMapActive.remove("default");
		
		for(String key : this.animationMapActive.keySet()) {
			if(!key.equals(animation.getName()))
				continue;
			
			this.animationMapActive.remove(key);
			break;
		}
		
		this.redrawAnimationMap();
	}
	
	public void deleteSprite(Animation animation, int spriteIndex) {
		Sprite[] spritesNew = new Sprite[Math.max(0, animation.getSprites().length-1)];
		for(int i = 0; i < spritesNew.length; i++) {
			spritesNew[i] = animation.getSprite(i < spriteIndex ? i : i + 1);
		}
		animation.setSprites(spritesNew);
		
		this.redrawAnimation();
	}
	
	////////////////////////
	//////////////////////// SETTERS
	////////////////////////
	
	public void setDefaultAnimation(Animation animationDefault) {
		this.animationMapActive.put("default", animationDefault);
		//tick radio button of respective AnimationPanel
		this.redrawAnimationMap();
	}
	
	public void setSelectedAnimation(Animation animation) {
		this.animationSelected = animation;
	}
}
