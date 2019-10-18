package com.javocode.autodeployer.deployment.domain.upload;

public class Application {
    private String fullPathOfFile;
    private String name;
    private String ext;
    private String dir;

    public Application(String fullFilePath) {
        this.fullPathOfFile = fullFilePath;
        setUpFields();
    }

    public String getFullPathOfFile() {
        return fullPathOfFile;
    }

    public String getName() {
        return name;
    }

    public String getExt() {
        return ext;
    }

    public String getDir() {
        return dir;
    }

    private void setUpFields() {
        String[] array = getFullPathOfFile().split("/");
        String fullname = array[array.length - 1];
        this.name = fullname.split("\\.")[0];
        this.ext = "." + fullname.split("\\.")[1];
        array[array.length - 1] = null;
        this.dir = String.join("/", removeNullElements(array)) + "/";
    }

    private String[] removeNullElements(String[] arr) {
        String[] nArr = new String[arr.length - 1];
        for (int i = 0; i < nArr.length; i++) {
            nArr[i] = arr[i];
        }
        return nArr;
    }
}
