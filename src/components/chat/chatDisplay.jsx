
import { useDispatch, useSelector } from "react-redux";
import ChatHeader from "./header/chatHeader";
import ChatMessages from "./message/chatMessages";
import { useEffect, useReducer } from "react";
import { getConversationMessages } from "../../features/chatSlice";
import ChatInput from "./input/chatInput";

export default function ChatDisplay() {
  const dispatch = useDispatch();
  const {activeConversation, messages} = useSelector((state)=>state.chat);
  const {user} = useSelector((state)=>state.user);
  const {token} = user;
  const values = {token, conversationId: activeConversation?._id};
  useEffect(()=>{
    if(activeConversation?._id){
      dispatch(getConversationMessages(values));
    }
  }, [activeConversation]);
  console.log("messages", messages);
  return (
    <div className="relative dark:bg-dark_bg_3   w-full h-full border-l  dark:border-l-dark_border_2 select-none overflow-hidden ">
      <div>
        <ChatHeader />
        <ChatMessages /> 
        <ChatInput />
      </div>
    </div>
  );

}
