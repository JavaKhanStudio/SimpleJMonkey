package spider;

import static com.jme3.animation.LoopMode.Loop;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.jme3.animation.AnimChannel;
import com.jme3.animation.AnimControl;
import com.jme3.app.SimpleApplication;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.light.AmbientLight;
import com.jme3.light.DirectionalLight;
import com.jme3.material.Material;
import com.jme3.material.RenderState;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.post.FilterPostProcessor;
import com.jme3.post.filters.CartoonEdgeFilter;
import com.jme3.post.filters.FogFilter;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.queue.RenderQueue;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.system.AppSettings;
import com.jme3.util.TangentBinormalGenerator;

/**
 * @author 3D model by Artur Bardowski
 * @author Java file modification by Simon Bedard
 */
public class Main extends SimpleApplication 
{

	// More demo here
	// https://store.jmonkeyengine.org/
	
    public static void main(String[] args) 
    {
        Main app = new Main();
        AppSettings settings = new AppSettings(true);
        settings.setHeight(750) ; 
        settings.setWidth(1000) ; 
        app.setSettings(settings);
        app.setShowSettings(false);
        app.start();

    }
    
    private Node object3dNode;
    
    private Spatial modelMesh;
    private Node modelMeshNode;
    
    private RigidBodyControl physicsObject3d;
    private Node floorNode;
    private Spatial modelBackdrop;
    private FilterPostProcessor fpp;
    private AnimControl control1;
    private AnimChannel channel1;

    @Override
    public void simpleInitApp() 
    {
    	Logger.getLogger( "" ).setLevel( Level.OFF );
		// OBJECT 3D ---
        object3dNode = new Node("supply_crate");
        modelMesh = assetManager.loadModel("Models/spiderex1/spiderex1.j3o");
        
        modelMeshNode =  (Node) modelMesh ;
        showNodesLevels() ; 
//        messWithMaterials() ; 
       
        
        TangentBinormalGenerator.generate(modelMesh);
        physicsObject3d = new RigidBodyControl(0f);
        object3dNode.setLocalTranslation(new Vector3f(0, -2.2f, 1));
        object3dNode.addControl(physicsObject3d);
        object3dNode.attachChild(modelMesh);
        rootNode.attachChild(object3dNode);

        control1 = object3dNode.getChild("spideRex.head").getControl(AnimControl.class);
        control1.getAnimationNames();

        //  Shadow
        Spatial shadow = assetManager.loadModel("Models/cien/cien.j3o");
        Material mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        mat.setTexture("ColorMap", assetManager.loadTexture("Models/cien/cien.png"));

        mat.setFloat("AlphaDiscardThreshold", 0.1f);
        shadow.setQueueBucket(RenderQueue.Bucket.Transparent);
        mat.getAdditionalRenderState().setBlendMode(RenderState.BlendMode.Alpha);
        shadow.setMaterial(mat);
        shadow.setLocalScale(4f, 1.6f, 4f);
        object3dNode.attachChild(shadow);
        
        // Floor
        floorNode = new Node("floor");
        modelBackdrop = assetManager.loadModel("Models/backdrop/backdrop.j3o");
        floorNode.setLocalTranslation(new Vector3f(0, -2.21f, 9));
        floorNode.addControl(physicsObject3d);
        floorNode.attachChild(modelBackdrop);
        rootNode.attachChild(floorNode);

        //FILTERS
        FogFilter fog = new FogFilter();
        fog.setFogColor(new ColorRGBA(0.9f, 0.9f, 0.8f, 1.0f));

        fog.setFogDistance(30);
        fog.setFogDensity(2f);
        
        CartoonEdgeFilter cartoon = new CartoonEdgeFilter() ;
        cartoon.setEdgeColor(ColorRGBA.Cyan);
        
        fpp = new FilterPostProcessor(assetManager);
//        fpp.addFilter(fog);
//        fpp.addFilter(cartoon);
        viewPort.addProcessor(fpp);

        // Light     
        DirectionalLight sun = new DirectionalLight();
        sun.setColor(ColorRGBA.White);
        sun.setDirection(new Vector3f(-3f, -3f, -3));
       
        
        AmbientLight everyWhere = new AmbientLight();
        everyWhere.setColor(new ColorRGBA(0.9f, 0.8f, 0.9f,1));
        everyWhere.setColor(new ColorRGBA(0.1f, 0.1f, 0.1f,1));
        
        
//        rootNode.addLight(sun);
        rootNode.addLight(everyWhere);

        channel1 = control1.createChannel();
        channel1.setAnim("Idle"); // Idle, walking , atack, die
        channel1.setLoopMode(Loop);
    }
    
    Node level2 ; 
    Node level3 ; 
    Node level4 ;     
    
    private void showNodesLevels()
    {
          System.out.println(modelMeshNode.getChildren() + " Check children level 1");
         
          level2 = (Node) modelMeshNode.getChildren().get(0) ;
          System.out.println(level2.getChildren() + " Check children level 2");
          
          level3 = (Node) level2.getChildren().get(level2.getChildren().size() - 1) ;
          System.out.println(level3.getChildren() + " Check children level 3");
          
          level4 = (Node) level3.getChildren().get(level3.getChildren().size() - 1) ;
          System.out.println(level4.getChildren() + " Check children level 3");
    }

    private void messWithMaterials()
    {
    	 modelMesh.setMaterial(assetManager.loadMaterial("Models/spiderex1/newMaterial.j3m"));
    	
    	 Geometry head = (Geometry) level4.getChild(0) ;
//    	 head.setMaterial(assetManager.loadMaterial("Models/spiderex1/spiderHead.j3m"));
//    	 head.setMaterial(assetManager.loadMaterial("Models/spiderex1/spiderHeadRED.j3m"));
    	 
    	 
    	 Geometry tail = (Geometry) level4.getChild(9) ;
//    	 tail.setMaterial(assetManager.loadMaterial("Models/spiderex1/WoodenTail.j3m"));
    	 
    }
    
    @Override
    public void simpleUpdate(float tpf) {
        
    }

    @Override
    public void simpleRender(RenderManager rm) 
    {
        
    }
}
