
package robotbuilder;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JPanel;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;
import robotbuilder.data.PaletteItem;
import robotbuilder.data.RobotComponent;

/**
 * The Palette is the set of components that can be used to create the robot
 * map. Each palette item represents a motor, sensor, or other component. These
 * are dragged to the robot tree.
 * @author brad
 */
public class Palette extends JPanel {
    
    private JTree paletteTree;
    static private Palette instance = null;
    private HashMap<String, PaletteItem> paletteItems = new HashMap<String, PaletteItem>();
    
    private Palette() {
        FileReader file;
        try {
            file = new FileReader((new File("PaletteDescription.json")).getAbsolutePath());
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Palette.class.getName()).log(Level.SEVERE, null, ex);
            return;
        }
        JSONTokener tokener;
        tokener = new JSONTokener(file);
        JSONObject json;
        try {
            json = new JSONObject(tokener);
        } catch (JSONException ex) {
            Logger.getLogger(Palette.class.getName()).log(Level.SEVERE, null, ex);
            return;
        }
        
        DefaultMutableTreeNode root = new DefaultMutableTreeNode("Palette");
        try {
            createTree(root, json.getJSONObject("Palette"));
        } catch (JSONException ex) {
            Logger.getLogger(Palette.class.getName()).log(Level.SEVERE, null, ex);
        }
        paletteTree = new JTree(root);
        paletteTree.setDragEnabled(true);
        
        add(paletteTree);
     }
    
    /**
     * Singleton getInstance method returns the single instance of the palette.
     * @return Palette instance
     */
    public static Palette getInstance() {
        if (instance == null)
            instance = new Palette();
        return instance;
    }
    
    /**
     * Get the paletteItem that corresponds to a name.
     * Each item on the palette has a unique name and this method returns the PaletteItem object that
     * corresponds to the given name.
     * @param name The name of the palette item
     * @return The PaletteItem for the given name
     */
    public PaletteItem getItem(String name) {
        PaletteItem item = paletteItems.get(name);
        return item;
    }

    /**
     * Build the palette tree recursively by traversing the JSON data object
     * @param root The parent tree node
     * @param jSONObject The JSON object that corresponds to this level
     */
    private void createTree(DefaultMutableTreeNode root, JSONObject jSONObject) {
        for (Iterator i = jSONObject.keys(); i.hasNext(); ) {
            String key = (String) i.next();
            JSONObject child;
            try {
                child = (JSONObject) jSONObject.get(key);
            } catch (JSONException ex) {
                Logger.getLogger(Palette.class.getName()).log(Level.SEVERE, null, ex);
                return;
            }
            if (!child.has("Properties")) {
                try {
                    //TODO: create the PaletteItem here
                    DefaultMutableTreeNode node = new DefaultMutableTreeNode(key);
                    root.add(node);
                    createTree(node, jSONObject.getJSONObject(key));
                } catch (JSONException ex) {
                    Logger.getLogger(Palette.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            else {
                RobotComponent component = createRobotComponent(key, child);
                DefaultMutableTreeNode node = new DefaultMutableTreeNode(component);
                root.add(node);
            }
        }
    }

    private RobotComponent createRobotComponent(String key, JSONObject child) {
        RobotComponent component = new RobotComponent(key);
        try {
            JSONObject props = child.getJSONObject("Properties");
            for (Iterator i = props.keys(); i.hasNext();) {
                String name = (String) i.next();
                JSONObject values = props.getJSONObject(name);
                for (Iterator v = values.keys(); v.hasNext(); ) {
                    System.out.println((String)v.next());
                }
                component.addProperty(name, "Properties");
            }
        } catch (JSONException ex) {
            Logger.getLogger(Palette.class.getName()).log(Level.SEVERE, null, ex);
        }
        component.print();
        return component;
    }
}
