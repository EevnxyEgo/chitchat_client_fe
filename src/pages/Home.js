import { useDispatch, useSelector } from "react-redux"
import {Sidebar} from "../components/sidebar/index.js"
import { useEffect } from "react";
import { getConversations } from "../features/chatSlice.js";
import { ChitChatHome, ChatDisplay } from "../components/chat";

export default function Home() {
    const dispatch = useDispatch();
    const {user} = useSelector((state) => state.user);
    const {activeConversation} = useSelector((state)=>state.chat);
    console.log("active",activeConversation);
    useEffect(()=>{
        if(user){
            dispatch(getConversations(user.token));
        }
    }, [user]);


    return <div className="h-screen dark:bg-dark_bg_1 flex items-center justify-center py-[19px] overflow-hidden">
        {/*container*/}
        <div className="container min-h-screen flex">
            {/*sidebar*/}
            <Sidebar/>
            {
                activeConversation._id ? <ChatDisplay/> : <ChitChatHome/>
            }
            
        </div>
    </div>
        
}

