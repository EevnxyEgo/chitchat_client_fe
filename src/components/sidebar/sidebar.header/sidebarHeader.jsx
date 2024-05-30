import { useDispatch, useSelector } from "react-redux";
import { useEffect, useState } from "react";
import { ChatIcon, CommunityIcon, DotsIcon, StoryIcon } from "../../../svg";
import Menu from "./menu";

export default function SidebarHeader() {
  const { user } = useSelector((state) => state.user);
  const [showMenu, setShowMenu] = useState(false);
  useEffect(() => {
    console.log(user);
  }, [user]);

  return (
    <div className="h-full w-full dark:bg-dark_bg_3 flex flex-col items-center p-4 relative">
      {/* User Image */}
      <button className="btn mb-4">
        {user.picture ? (
          <img
            src={user.picture}
            alt={user.name}
            className="w-12 h-12 rounded-full object-cover"
          />
        ) : (
          <span>No Image</span>
        )}
      </button>
      {/* Icons */}
      <ul className="flex flex-col items-center gap-y-6 mt-4 relative">
        <li>
          <button className="btn p-2">
            <CommunityIcon className="dark:fill-dark_svg_1" />
          </button>
        </li>
        <li>
          <button className="btn p-2">
            <StoryIcon className="dark:fill-dark_svg_1" />
          </button>
        </li>
        <li>
          <button className="btn p-2">
            <ChatIcon className="dark:fill-dark_svg_1" />
          </button>
        </li>
        <li className="relative">
          <button
            className={`btn p-2 ${showMenu ? "bg-dark_hover_1" : ""}`}
            onClick={() => setShowMenu((prev) => !prev)}
          >
            <DotsIcon className="dark:fill-dark_svg_1" />
          </button>
          {showMenu && <Menu />}
        </li>
      </ul>
    </div>
  );
}
