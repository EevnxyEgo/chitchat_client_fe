import { useState } from "react";
import { SendIcon } from "../../../svg";
import Attachment from "./attachment";
import Emoji from "./emoji";
import Input from "./input";
import { useDispatch, useSelector } from "react-redux";
import { sendMessage } from "../../../features/chatSlice";
import ClipLoader from "react-spinners/ClipLoader";

export default function ChatInput() {
    const dispatch = useDispatch();
    const {activeConversation, status} = useSelector((state)=>state.chat)
    const {user} = useSelector((state)=>state.user)
    const {token} = user;
    const [message, setMessage] = useState("");
    const values = {
        message,
        conversationId: activeConversation._id,
        files: [],
        token,
    }
    const SendMessageHandler= async(e)=>{
        e.preventDefault();
        await dispatch(sendMessage(values));
    }
    return (
        <form 
        onSubmit={(e)=>SendMessageHandler(e)}
        className="dark:bg-dark_bg_1 h-[60px] w-full flex items-center absolute bottom-0 py-2 px-4 select-none rounded-2xl">
            <div className="w-full flex items-center gap-x-2">
                <ul className="flex gap-x-2">
                    <Emoji />
                    <Attachment />
                </ul>
                <Input 
                message={message}
                setMessage={setMessage}
                />
                <button className="btn" type="submit"> 
                {
                    status==="loading" ? (<ClipLoader color="#E9EDEF" size={25} />) : (
                <SendIcon className="dark:fill-dark_svg_1" />
                )}
                </button>
            </div>
        </form>
    )
}
