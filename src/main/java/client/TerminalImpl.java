package client;

class TerminalImpl extends Terminal {

    private ProcessUtils processUtils;
    private String editor;
    private String viewer;
    private boolean screenBufferNormal = true;

    static TerminalImpl createInstanceWithEditorViewer(String editor, String viewer) {
        TerminalImpl terminal = new TerminalImpl(ProcessUtils.getInstance());
        terminal.setEditor(editor);
        terminal.setViewer(viewer);
        return terminal;
    }

    static TerminalImpl createInstanceWithProcessUtils(ProcessUtils processUtils) {
        return new TerminalImpl(processUtils);
    }

    private TerminalImpl(ProcessUtils processUtils) {
        this.processUtils = processUtils;
    }

    void editFile(String path) {
        setScreenBufferAlternate();
        processUtils.executeProcess(new String[] {editor, path});
        setScreenBufferNormal();
    }

    void viewFile(String path) {
        setScreenBufferAlternate();
        processUtils.executeProcess(new String[] {viewer, path});
        setScreenBufferNormal();
    }

    private void setScreenBufferNormal() {
        if (!screenBufferNormal) {
            String normBuff = "tput rmcup";
            processUtils.executeProcess(new String[]{"bash", "-c", normBuff});
            screenBufferNormal = true;
        }
    }

    private void setScreenBufferAlternate() {
        if (screenBufferNormal) {
            String altBuff = "tput smcup";
            processUtils.executeProcess(new String[]{"bash", "-c", altBuff});
            screenBufferNormal = false;
        }
    }

    private void setEditor(String editor) {
        this.editor = editor;
    }

    private void setViewer(String viewer) {
        this.viewer = viewer;
    }
}
