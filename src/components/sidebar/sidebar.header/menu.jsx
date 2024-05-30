import { useDispatch } from "react-redux";
import { logout } from "../../../features/userSlice";

export default function Menu() {
    const dispatch = useDispatch();
    return (
        <div className="absolute left-14 top-0 z-50 dark:bg-dark_bg_2 dark:text-dark_text_2 shadow-md w-52"> {/* Adjusted positioning */}
            <ul>
                <li className="py-3 pl-5 cursor-pointer hover:bg-dark_bg_3">
                    <span>New Group</span>
                </li>
                <li className="py-3 pl-5 cursor-pointer hover:bg-dark_bg_3">
                    <span>New Community</span>
                </li>
                <li className="py-3 pl-5 cursor-pointer hover:bg-dark_bg_3">
                    <span>Starred Message</span>
                </li>
                <li className="py-3 pl-5 cursor-pointer hover:bg-dark_bg_3">
                    <span>Settings</span>
                </li>
                <li className="py-3 pl-5 cursor-pointer hover:bg-dark_bg_3"
                    onClick={() => dispatch(logout())}>
                    <span>Logout</span>
                </li>
            </ul>
        </div>
    );
}
