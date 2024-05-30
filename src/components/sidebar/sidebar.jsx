import { SidebarHeader } from "./sidebar.header/index.js"
import { Notifications } from "./notifications/index.js"
import { Search } from "./search/index.js"
import { useState } from "react"
import { Conversations } from "./conversations/index.js"
import { SearchResults } from "./search"

export default function Sidebar() {
  const [searchResults, setSearchResults] = useState([])
  console.log(searchResults);
  return (
    <div className="flex h-full w-[700px] select-none">
      {/**Sidebar Header */}
      <div className="w-[15%] h-full flex-shrink-0"> {/* Added flex-shrink-0 */}
        <SidebarHeader />
      </div>
      <div className="w-[85%] h-full overflow-y-auto"> {/* Added overflow-y-auto */}
        {/**Notif */}
        <Notifications />
        {/**Search bar */}
        <Search 
          searchLength={searchResults.length}
          setSearchResults={setSearchResults}
        />
        {
          searchResults.length > 0 ? (
            <SearchResults searchResults={searchResults} />
          ) : (
            <Conversations />
          )
        }
      </div>
    </div>
  )
}
