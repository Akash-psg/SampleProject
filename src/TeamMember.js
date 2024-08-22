import React, { useEffect, useState } from 'react';
import { DragDropContext, Droppable, Draggable } from 'react-beautiful-dnd';
import './TeamMember.css';

const TeamMember = () => {
    const [milestones, setMilestones] = useState([]);
    const [tasks, setTasks] = useState([]);
    const [selectedTask, setSelectedTask] = useState(null);
    const [messageContent, setMessageContent] = useState('');
    const [isMessageFormVisible, setMessageFormVisible] = useState(false);

    const username = 'Teamember1'; // Update this as necessary
    const receiver = 'ProjectManager1'; // Update this as necessary

    useEffect(() => {
        const fetchData = async () => {
            try {
                const milestonesResponse = await fetch('http://localhost:8080/api/milestones');
                const milestonesData = await milestonesResponse.json();
                setMilestones(milestonesData);

                const tasksResponse = await fetch('http://localhost:8080/api/tasks/project/3');
                const tasksData = await tasksResponse.json();
                setTasks(tasksData);
            } catch (error) {
                console.error('Error fetching data:', error);
            }
        };

        fetchData();
    }, []);

    const combinedData = milestones.map(milestone => {
        const milestoneTasks = tasks.filter(task => task.milestone.id === milestone.id);
        return {
            ...milestone,
            tasks: milestoneTasks
        };
    });

    const handleDragEnd = async (result) => {
        const { destination, source, draggableId } = result;
    
        if (!destination) return;
    
        const taskId = draggableId.toString();
        const sourceMilestone = milestones.find(milestone => milestone.id === combinedData[source.droppableId]?.id);
        const destinationMilestone = milestones.find(milestone => milestone.id === combinedData[destination.droppableId]?.id);
        const draggedTask = tasks.find(task => task.id.toString() === taskId);
    
        if (!draggedTask || !sourceMilestone || !destinationMilestone) {
            console.error('Error: Task or milestone not found');
            return;
        }
    
        const updatedTask = {
            ...draggedTask,
            milestone: {
                id: destinationMilestone.id,
                name: destinationMilestone.name
            }
        };
    
        try {
            const response = await fetch(`http://localhost:8080/api/tasks/${taskId}/milestone`, {
                method: 'PUT',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify({ newMilestoneId: destinationMilestone.id })
            });
    
            if (response.ok) {
                alert('Task milestone updated successfully!');
                setMessageFormVisible(true);

                const message = {
                    sender: username,
                    receiver: receiver,
                    subject: 'Task Milestone Updated',
                    context: `Task '${draggedTask.taskName}' has been updated from milestone '${sourceMilestone.id}' to '${destinationMilestone.id}'.`
                };
    
                setMessageContent(JSON.stringify(message, null, 2));
    
                const updatedTasks = tasks.map(task =>
                    task.id.toString() === taskId ? updatedTask : task
                );
                setTasks(updatedTasks);
    
                const updatedMilestones = combinedData.map(milestone => {
                    if (milestone.id === sourceMilestone.id) {
                        return {
                            ...milestone,
                            tasks: milestone.tasks.filter(task => task.id.toString() !== taskId)
                        };
                    } else if (milestone.id === destinationMilestone.id) {
                        return {
                            ...milestone,
                            tasks: [...milestone.tasks, updatedTask]
                        };
                    }
                    return milestone;
                });
    
                setMilestones(updatedMilestones);
            } else {
                alert('Error updating task milestone.');
            }
        } catch (error) {
            console.error('Error updating task milestone:', error);
            alert('Error updating task milestone.');
        }
    };

    const handleMessageSubmit = async () => {
        try {
            const message = JSON.parse(messageContent);

            const response = await fetch('http://localhost:8080/api/messages', {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify(message)
            });

            if (response.ok) {
                alert('Message posted successfully!');
                setMessageContent('');
                setMessageFormVisible(false);
            } else {
                alert('Error posting message.');
            }
        } catch (error) {
            console.error('Error posting message:', error);
            alert('Error posting message.');
        }
    };

    return (
        <DragDropContext onDragEnd={handleDragEnd}>
            <Droppable droppableId="milestones" direction="horizontal">
                {(provided) => (
                    <div
                        className="milestones-container"
                        ref={provided.innerRef}
                        {...provided.droppableProps}
                    >
                        <h1 className="welcome-message">Team Member Page</h1>
                        <p className="welcome-text">Welcome!</p>

                        {combinedData.map((milestone, index) => (
                            <Droppable key={milestone.id.toString()} droppableId={index.toString()} direction="vertical">
                                {(provided) => (
                                    <div
                                        className="milestone"
                                        ref={provided.innerRef}
                                        {...provided.droppableProps}
                                    >
                                        <h3 className="milestone-title">{milestone.name}</h3>
                                        <p className="milestone-description">{milestone.description}</p>
                                        <div className="tasks">
                                            {milestone.tasks.length > 0 ? (
                                                <ul>
                                                    {milestone.tasks.map((task, taskIndex) => (
                                                        <Draggable key={task.id.toString()} draggableId={task.id.toString()} index={taskIndex}>
                                                            {(provided) => (
                                                                <li
                                                                    className="task-item"
                                                                    ref={provided.innerRef}
                                                                    {...provided.draggableProps}
                                                                    {...provided.dragHandleProps}
                                                                >
                                                                    <button 
                                                                        onClick={() => setSelectedTask(task)} 
                                                                        className="task-button"
                                                                    >
                                                                        {task.taskName}
                                                                    </button>
                                                                </li>
                                                            )}
                                                        </Draggable>
                                                    ))}
                                                </ul>
                                            ) : (
                                                <p>No tasks for this milestone.</p>
                                            )}
                                        </div>
                                    </div>
                                )}
                            </Droppable>
                        ))}

                        {provided.placeholder}
                    </div>
                )}
            </Droppable>

            {selectedTask && (
                <div className="task-details-overlay" onClick={() => setSelectedTask(null)}>
                    <div className="task-details" onClick={(e) => e.stopPropagation()}>
                        <button className="close-button" onClick={() => setSelectedTask(null)}>×</button>
                        <h2>Task Details</h2>
                        <div className="task-detail">
                            <strong>Task Name:</strong> {selectedTask.taskName}
                        </div>
                        <div className="task-detail">
                            <strong>Description:</strong> {selectedTask.description}
                        </div>
                        <div className="task-detail">
                            <strong>Status:</strong> {selectedTask.status}
                        </div>
                        <div className="task-detail">
                            <strong>Start Date:</strong> {new Date(selectedTask.startDate).toLocaleDateString()}
                        </div>
                        <div className="task-detail">
                            <strong>Due Date:</strong> {new Date(selectedTask.dueDate).toLocaleDateString()}
                        </div>
                    </div>
                </div>
            )}

            {isMessageFormVisible && (
                <div className="message-form-overlay" onClick={() => setMessageFormVisible(false)}>
                    <div className="message-form" onClick={(e) => e.stopPropagation()}>
                        <h2>Post a Message</h2>
                        <textarea
                            value={messageContent}
                            onChange={(e) => setMessageContent(e.target.value)}
                            placeholder="Enter your message here..."
                        />
                        <button onClick={handleMessageSubmit}>Submit</button>
                    </div>
                </div>
            )}
        </DragDropContext>
    );
};

export default TeamMember;