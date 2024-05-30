import { useDispatch, useSelector } from 'react-redux';
import { openCreateConversation } from '../../../features/chatSlice';
import { dateHandler } from '../../../utils/date.util';
import { getConversationId } from '../../../utils/chat.util';
import { capitalize } from '../../../utils/string.util';

export default function Conversation({conversation}) {
  const dispatch=useDispatch();
  const {user} = useSelector((state) => state.user)
  const {token} =  user;
  console.log(conversation)
  const values = {
    receiverId: getConversationId(user, conversation.users),
    token,
  };
  const openConversation = () => {
    dispatch(openCreateConversation(values))
  };
  return (
  <li 
  onClick={()=>openConversation()}
  className="list-none h-[72px] w-full dark:bg-dark_bg_1 hover:dark:bg-dark_bg_2 cursor-pointer dark:text-dark_text_1 px-[10px] ">
      <div className="relative w-full flex items-center justify-between py-[10px]">
        <div className="flex items-center gap-x-3">
          <div className="relative min-w-[50px] max-w-[50px] h-[50px] rounded-full overflow-hidden">
            <img src={conversation.picture} alt={conversation.name} className="w-full h-full object-cover" />
          </div>
          <div className="w-full flex flex-col">
            <h1 className="font-bold flex items-center gap-x-2">
              {capitalize(conversation.name)}
            </h1>
            <div>
              <div className="flex items-center gap-x-1 dark:text-dark_text_2">
                <div className="flex-1 items-center gap-x-1 dark:text-dark_text_2">
                  <p>
                    {conversation.latestMessage?.message.length >25 ? `${conversation.latestMessage?.message.substring(0,25)}...` : conversation.latestMessage?.message}
                  </p>
                </div>
              </div>
            </div>
          </div>
        </div>
        <div className="flex flex-col gap-y-4 items-end text-xs">
          <span className="dark:text-dark_text_2">
            {conversation.latestMessage?.createdAt ? dateHandler(conversation.latestMessage.createdAt) : ''}
          </span>
        </div>
      </div>
      <div className="ml-16 border-b dark:border-b-dark_border_1">
      </div>
    </li>
  )
}
