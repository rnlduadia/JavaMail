/*
 * Copyright (c) 2004 North Coast Software Inc.
 */

package managemail;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InvalidClassException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;


public class UserInfo extends HashMap {

    public static String USER_ID = "USER_ID";
    public static String USER_PWD = "USER_PWD";
    public static String USER_DEPT = "USER_DEPT";    
    public static String USER_NAME = "USER_NAME";
    public static String MAIL_HOST = "MAIL_HOST";    
    public static String MAIL_DIR = "MAIL_DIR";
    public static String FILE_INFO = "FILE_INFO";
    
    private static final long serialVersionUID = 20060901L;
  

    
    public boolean generate(Account userinfo, String filename) throws FileNotFoundException,
            IOException, java.text.ParseException {
        
        File ff = new File(filename);
        ff.createNewFile();
        
        this.put(UserInfo.USER_ID, userinfo.getId());
        this.put(UserInfo.USER_PWD, userinfo.getPwd());
        this.put(UserInfo.USER_NAME, userinfo.getName());
        this.put(UserInfo.USER_DEPT, userinfo.getPosition());
        this.put(UserInfo.MAIL_HOST, userinfo.getServer());
        this.put(UserInfo.MAIL_DIR, userinfo.getPath());
        this.put(UserInfo.FILE_INFO, filename);
        

        boolean flag = save(filename);
        return flag;
    }

    /**
     * Save the contents to a file.
     */
    public boolean save(String filename) {
        // Encode something large to file, NOT gzipped
        // ObjectOutput -> Base64 -> Buffer -> File

        // System.out.print("\n\nWriting to file: " + filename);
        java.io.ObjectOutputStream oos = null;
        Base64.OutputStream b64os = null;
        java.io.BufferedOutputStream bos = null;
        java.io.FileOutputStream fos = null;

        try {
            fos = new java.io.FileOutputStream(filename);
            bos = new java.io.BufferedOutputStream(fos);
            b64os = new Base64.OutputStream(bos, Base64.ENCODE);
            oos = new java.io.ObjectOutputStream(b64os);

            oos.writeObject(this);
        } // end try
        catch (java.io.IOException e) {
            e.printStackTrace();
        } // end catch
        finally {
            try {
                if(oos != null) oos.close();
            } catch (Exception e) {
                // e.printStackTrace();
                // return false;
            }
            try {
                b64os.close();
            } catch (Exception e) {
                // e.printStackTrace();
                // return false;
            }
            try {
                bos.close();
            } catch (Exception e) {
                // e.printStackTrace();
                // return false;
            }
            try {
                fos.close();
            } catch (Exception e) {
                // e.printStackTrace();
                // return false;
            }
            // System.out.println(" ...Done.");
        } // end finally
        return true;
    }

    /**
     * Loads the contents of a previously serialized license from a file.
     */
    public static UserInfo load(String filename) {

        UserInfo userinfo = null;
        // Read back in
        // File -> Buffer -> Base64 -> Object
        // System.out.print("\n\nReading from file " + filename);
        java.io.ObjectInputStream ois = null;
        Base64.InputStream b64is = null;
        java.io.BufferedInputStream bis = null;
        java.io.FileInputStream fis = null;

        try {
            fis = new java.io.FileInputStream(filename);
            bis = new java.io.BufferedInputStream(fis);
            b64is = new Base64.InputStream(bis, Base64.DECODE);
            ois = new java.io.ObjectInputStream(b64is);

            Object obj = ois.readObject();
            userinfo = (UserInfo) obj;
        } // end try
        catch (InvalidClassException e) {
            throw new RuntimeException("Invalid License File: " + filename, e);
        }

        catch (java.io.FileNotFoundException e) {
            throw new RuntimeException("License File not found: " + filename, e);
        }

        catch (java.io.IOException e) {
            throw new RuntimeException("java.io.IOException: " + filename, e);
        }

        catch (java.lang.ClassNotFoundException e) {
            throw new RuntimeException("License Class not found!", e);
        }

        finally {
            try {
                if (ois != null)
                    ois.close();
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
            try {
                if (b64is != null)
                    b64is.close();
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
            try {
                if (bis != null)
                    bis.close();
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
            try {
                if (fis != null)
                    fis.close();
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
            // System.out.println(" ...Done.");
        } // end finally

        // System.out.println("Printing out loaded elements...");
        Set set = userinfo.keySet();
        Iterator iter = set.iterator();
        while (iter.hasNext()) {
            Object obj = iter.next();
            // String str = " - Element(" + obj + ") = " + license.get(obj);
            // System.out.println(str);

        }
        // System.out.println();
        return userinfo;
    }


}
