class Utils {
    
    static String loadResource(String path) throws IOException {
        return new File(path).getText('UTF-8');
    }

}
