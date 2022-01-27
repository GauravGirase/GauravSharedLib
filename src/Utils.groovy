class Utils {
    
     /**
     * Read resource file
     *
     * @Example
     * <pre>
     *     String content = Utils.loadResource('src/resources/my-file.txt')
     * </pre>
     *
     * @param  path        path to the resource file
     * @return             the file content
     * @throws IOException if failed to read the file
     */
    
    static String loadResource(String path) throws IOException {
        return new File(path).getText('UTF-8');
    }

}
