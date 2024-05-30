import { useRef, useState } from "react";

const Picture = ({ readablePicture, setReadablePicture, setPicture }) => {
    const [error, setError] = useState("");
    const inputRef = useRef();

    const handlePicture = (e) => {
        const pic = e.target.files[0];
        if (!pic) return; // Handle ketika tidak ada gambar yang dipilih
        if (
            pic.type !== "image/jpeg" &&
            pic.type !== "image/png" &&
            pic.type !== "image/webp"
        ) {
            setError(`${pic.name} format is not supported.`);
            return;
        } else if (pic.size > 1024 * 1024 * 5) {
            setError(`${pic.name} size is too large, maximum 5mb allowed.`);
            return;
        } else {
            setError("");
            setPicture(pic);
            const reader = new FileReader();
            reader.readAsDataURL(pic);
            reader.onload = (e) => {
                setReadablePicture(e.target.result);
            };
        }
    };

    const handleChangePic = () => {
        setPicture(null); // Menghapus gambar
        setReadablePicture(""); // Menghapus gambar yang terbaca
    };

    return (
        <div className="mt-8 content-center dark:text-dark_text_1 space-y-1">
            <label htmlFor="picture" className="text-sm font-bold tracking-wide">
            </label>
            <div className="flex justify-center">
                {readablePicture ? (
                    <div>
                        <div
                            className="w-32 h-32 relative rounded-full overflow-hidden"
                            onClick={handleChangePic}
                        >
                            <img
                                src={readablePicture}
                                alt="picture"
                                className="w-full h-full object-cover"
                            />
                            <div className="absolute inset-0 flex items-center justify-center bg-black bg-opacity-50 hover:bg-opacity-70 transition duration-300 cursor-pointer">
                                <span className="text-white text-sm font-bold">Remove</span>
                            </div>
                        </div>
                    </div>
                ) : (
                    <div
                        className="w-32 h-32 relative rounded-full flex items-center justify-center cursor-pointer"
                        onClick={() => inputRef.current.click()}
                    >
                        <input
                            type="file"
                            name="picture"
                            id="picture"
                            hidden
                            ref={inputRef}
                            accept="image/png, image/jpeg, image/webp"
                            onChange={handlePicture}
                        />
                        <div className="absolute inset-0 flex items-center justify-center bg-black bg-opacity-50 hover:bg-opacity-70 transition duration-300 cursor-pointer rounded-full">
                            <span className="text-white text-sm font-bold text-center">Upload Picture (Optional)</span>
                        </div>
                    </div>
                )}
            </div>
            <div className="mt-2">
                <p className="text-red-400 text-center">{error}</p>
            </div>
        </div>
    );
};

export default Picture;
