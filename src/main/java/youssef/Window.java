package youssef;
//42:24
import org.lwjgl.Version;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.opengl.GL;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.NULL;

public class Window {
   private  int width,height;
    private String title;
    private static Window window=null;
    private long glfwWindow;

    private Window(){
        this.width=1920;
        this.height=1080;
        this.title="super mario by youssef";
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

        // Make the opengl context current
        glfwMakeContextCurrent(glfwWindow);

        // enable v-sync
        glfwSwapInterval(1);

        // make the window visible
        glfwShowWindow(glfwWindow);


        GL.createCapabilities();


    }
    public void loop(){
            while(!glfwWindowShouldClose(glfwWindow)){
                //poll events
                glfwPollEvents();

                glClearColor(1.0f,1.0f,1.0f,1.0f);
                glClear(GL_COLOR_BUFFER_BIT);

                glfwSwapBuffers(glfwWindow);
            }
    }
}
