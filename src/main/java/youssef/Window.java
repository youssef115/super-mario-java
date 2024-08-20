package youssef;

import org.lwjgl.Version;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.opengl.GL;
import utils.Time;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.NULL;

public class Window {
   private  int width,height;
    private String title;
    private static Window window=null;
    private long glfwWindow;
    public float r,g,b,a;

    private static Scene currentScene=null;

    private Window(){
        this.width=1920;
        this.height=1080;
        this.title="super mario by youssef";
        this.r=1;
        this.g=1;
        this.b=1;
        this.a=1;
    }

    public static void changeScene(int newScene){
        switch (newScene){
            case 0:
                currentScene = new LevelEditorScene();
                //currentScene.inti();
                break;
            case 1:
                currentScene =new LevelScene();
                break;
            default:
                assert false :"unknown scene "+newScene + "'";
                break;
        }
    }

    public static Window get(){
       if (Window.window==null){
           Window.window=new Window();
       }
       return Window.window;
    }

    public void run(){
        System.out.println("hello lwjgl "+ Version.getVersion()+"!");

        init();
        loop();

        // free the memory
        glfwFreeCallbacks(glfwWindow);
        glfwDestroyWindow(glfwWindow);

        // Terminate GLFW and free the error callback
        glfwTerminate();
        glfwSetErrorCallback(null).free();
    }
    public void init(){
        // setup an error callback
        GLFWErrorCallback.createPrint(System.err).set();

        //Initialize GLFW
        if(!glfwInit()){
            throw new IllegalStateException("Unable to initilize GLFW ");
        }

        // Configure GLFW
        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_VISIBLE,GLFW_FALSE);
        glfwWindowHint(GLFW_RESIZABLE,GLFW_TRUE);
        glfwWindowHint(GLFW_MAXIMIZED,GLFW_TRUE);

        // create the window
        // this is the memory address where the window is saved

        glfwWindow=glfwCreateWindow(this.width,this.height,this.title,NULL,NULL);
        if (glfwWindow==NULL){
            throw new IllegalStateException("Faild to create the GLFW window");
        }

        glfwSetCursorPosCallback(glfwWindow,MouseListener::mousePosCallback);
        glfwSetMouseButtonCallback(glfwWindow,MouseListener::mouseButtonCallback);
        glfwSetScrollCallback(glfwWindow,MouseListener::mouseScrollCallback);

        glfwSetKeyCallback(glfwWindow,KeyListener::keyCallback);

        // Make the opengl context current
        glfwMakeContextCurrent(glfwWindow);

        // enable v-sync
        glfwSwapInterval(1);

        // make the window visible
        glfwShowWindow(glfwWindow);


        GL.createCapabilities();

        Window.changeScene(0);

    }
    public void loop(){
        float beginTime= Time.getTime();
        float endTime;
        float dt=-1.0f;
            while(!glfwWindowShouldClose(glfwWindow)){
                //poll events
                glfwPollEvents();

                glClearColor(r,g,b,a);
                glClear(GL_COLOR_BUFFER_BIT);

                if(dt>=0)
                currentScene.update(dt);

                if(KeyListener.isKeyPressed(GLFW_KEY_SPACE)){
                    System.out.println("space key is pressed");
                }

                glfwSwapBuffers(glfwWindow);

                endTime=Time.getTime();
                 dt=endTime-beginTime;
                beginTime=endTime;
            }
    }
}
