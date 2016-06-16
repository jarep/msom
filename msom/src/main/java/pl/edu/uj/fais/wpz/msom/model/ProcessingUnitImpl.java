/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.edu.uj.fais.wpz.msom.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import pl.edu.uj.fais.wpz.msom.entities.Module;
import pl.edu.uj.fais.wpz.msom.entities.TaskType;
import pl.edu.uj.fais.wpz.msom.model.exceptions.ProcessingAbilityException;
import pl.edu.uj.fais.wpz.msom.model.interfaces.ProcessingUnit;
import pl.edu.uj.fais.wpz.msom.model.interfaces.Task;
import pl.edu.uj.fais.wpz.msom.model.interfaces.TaskDispatcher;
import pl.edu.uj.fais.wpz.msom.model.interfaces.Type;
import pl.edu.uj.fais.wpz.msom.service.interfaces.ControllerUnitService;
import pl.edu.uj.fais.wpz.msom.service.interfaces.ModuleService;
import pl.edu.uj.fais.wpz.msom.service.interfaces.TaskService;
import pl.edu.uj.fais.wpz.msom.service.interfaces.TaskTypeService;

/**
 *
 * @author jarep
 */
public class ProcessingUnitImpl extends AbstractModelObject<Module> implements ProcessingUnit {
    
    public enum State {IDLE, PROCESSING}
    
    /**
     * Callback for notifications about finished task
     */
    public interface ComputationCallback {
        void onFinished();
    }
   
    /**
     * Fake computation class for mocking time spent on computing task.
     */
    private static class FakeComputation implements Runnable {

        private final long timeMillis;
        private final ComputationCallback callback;

        public FakeComputation(long timeMillis, ComputationCallback callback) {
            this.timeMillis = timeMillis;
            this.callback = callback;
        }
       
        @Override
        public void run() {
            try {
               // HEAVY COMPUTATION. NOW!
               // Naaah, let me sleep...
               Thread.sleep(timeMillis);
            } catch (InterruptedException e) {
               e.printStackTrace();
            } finally {
               callback.onFinished();
            }
        }
        
    }
   

    // services
    private final ControllerUnitService controllerUnitService;
    private final ModuleService moduleService;
    private final TaskService taskService;
    private final TaskTypeService taskTypeService;

    // Simulation tools
    private State currentState = State.IDLE;
    private Queue<Task> queue = new ConcurrentLinkedQueue<>();
    private ExecutorService executor = Executors.newSingleThreadExecutor();
    
    // Data
    private String name;
    private int efficiency;
    private int cores;
    private int queueDifficultySum;
    private final Set<Type> availableTypes = new HashSet();
    private TaskDispatcher taskDispatcher;

    public ProcessingUnitImpl(Module entityObject, ControllerUnitService controllerUnitService, ModuleService moduleService, TaskService taskService, TaskTypeService taskTypeService) {
        this.controllerUnitService = controllerUnitService;
        this.moduleService = moduleService;
        this.taskService = taskService;
        this.taskTypeService = taskTypeService;
        setEntityObject(entityObject);
    }

    @Override
    public void reload() {
        reloadEntityObcject();
        reloadAvailableTypes();
    }

    private void reloadEntityObcject() {
        if (getEntityObject() != null) {
            moduleService.refresh(getEntityObject());
        }
    }

    private void reloadAvailableTypes() {
        availableTypes.clear();
        if (getEntityObject() != null) {
            Set<TaskType> taskTypes = getEntityObject().getTaskTypes();
            for (TaskType t : taskTypes) {
                TypeMockup type = new TypeMockup(t, taskTypeService);
                availableTypes.add(type);
            }
        }
    }

    @Override
    public void save() {
        saveEntityObject();
//        saveTaskTypes();
    }

    private void saveEntityObject() {
        if (getEntityObject() != null) {
            moduleService.update(getEntityObject());
        }
    }

    @Override
    public void setName(String name) {
       this.name = name;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public void setEfficiency(Integer efficiency) {
        this.efficiency = efficiency; 
    }

    @Override
    public Integer getEfficiency() {
        return this.efficiency;
    }

    @Override
    public void setCores(Integer cores) {
        this.cores = cores;
    }

    @Override
    public Integer getCores() {
        return this.cores;
    }

    @Override
    public void addType(Type type) {
       this.availableTypes.add(type);
    }

    @Override
    public void removeType(Type type) {
      this.availableTypes.remove(type);
    }

    @Override
    public List<Type> getAvailableTypes() {
        return new ArrayList<>(this.availableTypes);
    }

    @Override
    public void setTaskDispatcher(TaskDispatcher taskDispatcher) {
        this.taskDispatcher = taskDispatcher;
    }

    @Override
    public void processTask(Task task) throws ProcessingAbilityException {
        if (!this.availableTypes.contains(task.getType())){
            throw new ProcessingAbilityException();
        }
        task.queueTask();
        this.queue.add(task);
        driveQueue();
    }

    @Override
    public int getQueueLength() {
      return this.queue.size();
    }

    /**
     * For now it is only mockup - should be implemented
     */
    @Override
    public int getQueueValue() {
       return this.queueDifficultySum;
    }

    @Override
    public List<Task> getWaitingTasks() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Task> getProcessingTasks() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int getNumberOfProcessingTasks() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public double getPercentOfExecutionCurrentTasks() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int getAvgWaitingTime() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int getAvgProcessingTime() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    /**
     * Take next task from queue and process it
     */
    private void driveQueue() {
        if (!queue.isEmpty()) {
            Task task = queue.poll();
            long computationTime = calculateProcessingTime(task.getDifficulty(), this.efficiency);
            executor.submit(new FakeComputation(computationTime, new ComputationCallback() {
                @Override
                public void onFinished() {
                    
                }
            }));
        }
    }
    
    /**
     * Calculates time for fake processing.
     * @param difficulty task difficulty
     * @param cores processing unit cores
     * @param efficiency processing unit efficiency per core
     * @return calculated time
     */
    private long calculateProcessingTime(long difficulty, int efficiency) {
        return (long) difficulty / efficiency;
    }

}
