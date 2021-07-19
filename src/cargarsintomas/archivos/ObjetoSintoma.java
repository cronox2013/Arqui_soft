package cargarsintomas.archivos;

import monitor.Sintoma;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class ObjetoSintoma {

    public Sintoma crearObjeto(String nonSintoma, String nombre){
        Sintoma s = null;
        Class<?> c;
        try {
            c = Class.forName("sintomas."+nombre);
            Constructor<?> cons = c.getConstructor(String.class);
            Object object = cons.newInstance(new Object[] { nonSintoma });
            s = (Sintoma) object;
        } catch (ClassNotFoundException classNotFoundException) {
            classNotFoundException.printStackTrace();
        } catch (NoSuchMethodException classNotFoundException) {

        } catch (IllegalAccessException illegalAccessException) {
            illegalAccessException.printStackTrace();
        } catch (InstantiationException instantiationException) {
            instantiationException.printStackTrace();
        } catch (InvocationTargetException invocationTargetException) {
            invocationTargetException.printStackTrace();
        }
        return s;
    }
}
