import { Link } from "react-router-dom";

export default function ChitChatHome() {
  const logoUrl = "https://res.cloudinary.com/dnnwlqjk8/image/upload/v1716931493/najzbpuabsurwiunllkv.png";

  return (
    <div className="h-full w-full dark:bg-dark_bg_4 select-none border-l dark:border-l-dark_border_2 border-b-[6px] border-b-green_2">
      {/* Container */}
      <div className="w-full h-full flex flex-col gap-y-8 items-center justify-center">
        {/* Logo */}
        <span>
          <img src={logoUrl} alt="ChitChat Logo" className="h-32 w-32" />
        </span>
        {/* Infos */}
        <div className="text-center space-y-3">
          {/* Heading */}
          <h1 className="text-[36px] dark:text-dark_text_4 font-light">
            ChitChat
          </h1>
          {/* Description */}
          <p className="text-lg dark:text-dark_text_3">
            Select a chat to start messaging
          </p>
          {/* Button */}
          <Link to="/dashboard" className="bg-green_2 text-white py-2 px-6 rounded-md hover:bg-green_3 transition duration-300">Go to Dashboard</Link>
        </div>
      </div>
    </div>
  );
}
