package exemples;

import com.jme3.app.SimpleApplication;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.AnalogListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.scene.Geometry;
import com.jme3.scene.shape.Box;

/**
 * Sample 5 - how to map keys and mousebuttons to actions
 */
public class B_Inputs extends SimpleApplication 
{

    public static void main(String[] args) 
    {
        B_Inputs app = new B_Inputs();
        app.start();
    }

    protected Geometry player;
    private boolean isRunning = true;
    Material mat ; 

    @Override
    public void simpleInitApp() 
    {
        Box b = new Box(1, 1, 1);
        player = new Geometry("Player", b);
        mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        mat.setColor("Color", ColorRGBA.Blue);
        player.setMaterial(mat);
        rootNode.attachChild(player);
        initKeys(); // load my custom keybinding
    }

    /**
     * Custom Keybinding: Map named actions to inputs.
     */
    private void initKeys() 
    {
        // You can map one or several inputs to one named action
        inputManager.addMapping("RED",  new KeyTrigger(KeyInput.KEY_1));
        inputManager.addMapping("BLUE",   new KeyTrigger(KeyInput.KEY_2));
        inputManager.addMapping("YELLOW",  new KeyTrigger(KeyInput.KEY_3));
        inputManager.addMapping("CHANGE_ONCE",  new KeyTrigger(KeyInput.KEY_4));
        inputManager.addMapping("CHANGE",  new KeyTrigger(KeyInput.KEY_5));
        
        
        // Add the names to the action listener.
        inputManager.addListener(actionListener, "RED", "BLUE", "YELLOW", "CHANGE_ONCE");
        inputManager.addListener(analogListener, "CHANGE");

    }

    private final ActionListener actionListener = new ActionListener() {
        @Override
        public void onAction(String name, boolean keyPressed, float tpf) {
            if (name.equals("RED") && !keyPressed) {
            	 mat.setColor("Color", ColorRGBA.Red);
            }
            if (name.equals("BLUE") && !keyPressed) {
           	 	mat.setColor("Color", ColorRGBA.Blue);
            }
            if (name.equals("YELLOW") && !keyPressed) {
            	mat.setColor("Color", ColorRGBA.Yellow);
            }
            if (name.equals("CHANGE_ONCE") && !keyPressed) {
            	mat.setColor("Color", ColorRGBA.randomColor());
            }
        }
    };

    private final AnalogListener analogListener = new AnalogListener() {
        @Override
        public void onAnalog(String name, float value, float tpf) {
        	
        	if (name.equals("CHANGE")) {
           	 mat.setColor("Color", ColorRGBA.randomColor());
           }
        }
    };
}
