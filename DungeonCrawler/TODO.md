
##General
- Implement different store method of scenes. **Reason:** Changes to GameObjects will not take effect on already existing/exported scenes,
	as all attribute values are stored in the serialized version located in the scene.
	=> The scene should only have a quite minimal list of gameobject-names and basic values which are sufficient to initialize them
		when loading the scene. That way changes to all kinds of behaivior of the gameobject is decoupled from the scene
			=> Backwards-compatability!



##Animationeditor
- Add Button to quick add a new GameObject
- Add Hitbox-editor

##Leveleditor
- ~~Add Button to refresh/reload ObjectChooser~~ (not possible as a reload would require recompiling the source code of the program itself)



---

## Done
- Hitbox offset
- Leveleditor: Zoom relative to mouse cursor
- Leveleditor: Checkbox to show/hide hitboxes
